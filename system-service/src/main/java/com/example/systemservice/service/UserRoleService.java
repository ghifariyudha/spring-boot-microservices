package com.example.systemservice.service;

import com.example.systemservice.domain.entity.UserRole;
import com.example.systemservice.payload.response.UserRoleResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService {
    UserRole save(UserRole req);
    Boolean delete(Long id);
    UserRoleResp getOne(Long id);
    List<UserRoleResp> findAll();
    Page<UserRoleResp> findAll(Pageable pgb);
}
