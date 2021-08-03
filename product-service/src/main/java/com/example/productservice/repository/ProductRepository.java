package com.example.productservice.repository;

import com.example.productservice.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdProductAndIsActiveTrue(Long id);
    List<Product> findByIsActiveTrue(Sort sort);
    Page<Product> findByIsActiveTrue(Pageable pgb);
}
