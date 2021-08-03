package com.example.systemservice.service;

import com.example.systemservice.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role req);
    Boolean delete(Long id);
    Optional<Role> getOne(Long id);
    List<Role> findAll();
    Page<Role> findAll(Pageable pgb);
}
