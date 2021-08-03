package com.example.systemservice.repository;

import com.example.systemservice.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByIsActiveTrue(Sort sort);
    Page<Role> findByIsActiveTrue(Pageable pgb);
}
