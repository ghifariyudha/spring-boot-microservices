package com.example.systemservice.service;

import com.example.systemservice.domain.entity.User;
import com.example.systemservice.payload.request.AuthReq;
import com.example.systemservice.payload.response.AuthResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Boolean delete(Long id);
    Optional<User> getOne(Long id);
    List<User> findAll();
    Page<User> findAll(Pageable pgb);
}
