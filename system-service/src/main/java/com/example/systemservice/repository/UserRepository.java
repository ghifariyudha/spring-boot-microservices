package com.example.systemservice.repository;

import com.example.systemservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndIsActiveTrue(String username);
    User getByUsernameAndTokenAndIsActiveTrue(String username, String token);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByTokenAndIsActiveTrue(String token);
    List<User> findByIsActiveTrue(Sort sort);
    Page<User> findByIsActiveTrue(Pageable pgb);
}
