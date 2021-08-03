package com.example.systemservice.service;

import com.example.systemservice.domain.entity.User;
import com.example.systemservice.payload.request.AuthReq;
import com.example.systemservice.payload.response.AuthResp;

public interface AuthService {
    User register(User req);
    AuthResp login(AuthReq req);
    Boolean logout(String token);
}
