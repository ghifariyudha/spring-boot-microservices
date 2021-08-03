package com.example.orderservice.repository;

import com.example.orderservice.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdOrderAndIsActiveTrue(Long id);
    List<Order> findByIsActiveTrue(Sort sort);
    Page<Order> findByIsActiveTrue(Pageable pgb);
}
