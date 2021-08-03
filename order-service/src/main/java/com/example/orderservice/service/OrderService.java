package com.example.orderservice.service;

import com.example.orderservice.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(String token, Order req);
    Boolean delete(Long id);
    Optional<Order> getOne(Long id);
    List<Order> findAll();
    Page<Order> findAll(Pageable pgb);
}
