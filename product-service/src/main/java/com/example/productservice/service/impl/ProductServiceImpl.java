package com.example.productservice.service.impl;

import com.example.productservice.config.RedisConfig;
import com.example.productservice.domain.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;
    @Autowired
    RedisTemplate<String, Product> redisTemplate;

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "productList", allEntries = true),
            @CacheEvict(cacheNames = "productPage", allEntries = true)})
    public Product save(Product req) {
        Product product = new Product();
        product.setProductName(req.getProductName());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setDescription(req.getDescription());
        if (req.getIdProduct()!=null) {
            product = repository.getById(req.getIdProduct());
            product.setProductName(req.getProductName());
            product.setPrice(req.getPrice());
            product.setStock(req.getStock());
            product.setDescription(req.getDescription());
        }
        String redisKey = "product::"+req.getIdProduct();
        boolean hasKey = redisTemplate.hasKey(redisKey);
        if (hasKey) {
            redisTemplate.opsForValue().set(redisKey, product, RedisConfig.REDIS_EXPIRATION);
        }
        return repository.save(product);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "productList", allEntries = true),
            @CacheEvict(cacheNames = "productPage", allEntries = true)})
    public Boolean delete(Long id) {
        String redisKey = "product::"+id;
        boolean hasKey = redisTemplate.hasKey(redisKey);
        if (hasKey) {
            redisTemplate.delete(redisKey);
        }
        Optional<Product> optional = repository.findByIdProductAndIsActiveTrue(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setIsActive(false);
            repository.save(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Product getOne(Long id) {
        String redisKey = "product::"+id;
        boolean hasKey = redisTemplate.hasKey(redisKey);
        if (hasKey) {
            Product product = redisTemplate.opsForValue().get(redisKey);
            return product;
        }
        Optional<Product> opProduct = repository.findByIdProductAndIsActiveTrue(id);
        if (opProduct.isPresent()) {
            redisTemplate.opsForValue().set(redisKey, opProduct.get(), RedisConfig.REDIS_EXPIRATION);
            return opProduct.get();
        } else {
            return null;
        }
    }

    @Override
    @Cacheable(cacheNames = "productList")
    public List<Product> findAll() {
        return repository.findByIsActiveTrue(Sort.by("idProduct").descending());
    }

    @Override
    @Cacheable(cacheNames = "productPage")
    public Page<Product> findAll(Pageable pgb) {
        return repository.findByIsActiveTrue(pgb);
    }
}
