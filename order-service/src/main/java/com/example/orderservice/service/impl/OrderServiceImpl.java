package com.example.orderservice.service.impl;

import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.payload.ProductReq;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String PRODUCT_BY_ID_URI           = "http://localhost:8004/product/get";
    private static final String PRODUCT_SAVE_URI            = "http://localhost:8004/product/save";

    @Value("${kafka.topics.orderCreated}")
    private String TOPIC;
    @Autowired
    OrderRepository repository;
    @Autowired
    KafkaTemplate<String, Order> kakfaProducer;

    @Override
    @Transactional
    public Order save(String token, Order req) {
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put(HttpHeaders.CONTENT_TYPE, "application/json");
        mapHeader.put(HttpHeaders.AUTHORIZATION, token);
        HttpResponse<JsonNode> responseGet = Unirest.get(PRODUCT_BY_ID_URI)
                .headers(mapHeader)
                .queryString("id", req.getIdProduct())
                .asJson();
        if (responseGet.getStatus() == 404){
            throw new RuntimeException("Product Not Found!");
        }
        JSONObject jsonObject = responseGet.getBody().getObject();
        if (req.getQty() > jsonObject.optLong("stock")) {
            throw new RuntimeException("Not Enough Stock!");
        }
        Order order = new Order();
        order.setIdUser(req.getIdUser());
        order.setEmailUser(req.getEmailUser());
        order.setIdProduct(jsonObject.optLong("idProduct"));
        order.setProductName(jsonObject.optString("productName"));
        order.setQty(req.getQty());
        order.setTotal(jsonObject.optLong("price") * req.getQty());
        repository.save(order);

        ProductReq productReq = new ProductReq();
        productReq.setIdProduct(jsonObject.optLong("idProduct"));
        productReq.setProductName(jsonObject.optString("productName"));
        productReq.setPrice(jsonObject.optLong("price"));
        productReq.setStock(jsonObject.optLong("stock") - order.getQty());
        productReq.setDescription(jsonObject.optString("description"));
        HttpResponse<JsonNode> responsePost = Unirest.post(PRODUCT_SAVE_URI)
                .headers(mapHeader)
                .body(productReq)
                .asJson();
        if (responsePost.getStatus() != 200) {
            throw new RuntimeException("Failed to update Product!");
        }
        kakfaProducer.send(TOPIC, order);
        return order;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Order> optional = repository.findById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setIsActive(false);
            repository.save(order);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Order> getOne(Long id) {
        return repository.findByIdOrderAndIsActiveTrue(id);
    }

    @Override
    public List<Order> findAll() {
        return repository.findByIsActiveTrue(Sort.by("idOrder").descending());
    }

    @Override
    public Page<Order> findAll(Pageable pgb) {
        return repository.findByIsActiveTrue(pgb);
    }
}
