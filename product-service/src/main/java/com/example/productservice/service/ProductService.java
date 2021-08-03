package com.example.productservice.service;

import com.example.productservice.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product save(Product req);
    Boolean delete(Long id);
    Product getOne(Long id);
    List<Product> findAll();
    Page<Product> findAll(Pageable pgb);
}
