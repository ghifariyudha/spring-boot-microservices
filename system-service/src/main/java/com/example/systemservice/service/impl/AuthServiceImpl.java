package com.example.systemservice.service.impl;

import com.example.systemservice.domain.entity.User;
import com.example.systemservice.payload.request.AuthReq;
import com.example.systemservice.payload.response.AuthResp;
import com.example.systemservice.repository.UserRepository;
import com.example.systemservice.repository.UserRoleRepository;
import com.example.systemservice.security.JwtUtils;
import com.example.systemservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public User register(User req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        if (req.getIdUser()!=null) {
            user = userRepository.getById(req.getIdUser());
            user.setUsername(req.getUsername());
            user.setPassword(encoder.encode(req.getPassword()));
            user.setEmail(req.getEmail());
            user.setIsActive(req.getIsActive());
        }
        return userRepository.save(user);
    }

    @Override
    public AuthResp login(AuthReq req) {
        Optional<User> optionalUser = userRepository.findByUsernameAndIsActiveTrue(req.getUsername());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Username not found : "+req.getUsername());
        }
        User user = optionalUser.get();
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        //Generate JWT Token
        String jwt = JwtUtils.generateJwtToken(user);
        user.setToken(jwt);
        user.setTokenExpired(new Date((new Date()).getTime() + JwtUtils.JWT_TOKEN_VALIDITY));
        userRepository.save(user);
        List<String> userRoleList = userRoleRepository.findByUser_IdUserAndIsActiveTrue(user.getIdUser()).stream().map(item -> item.getRole().getRoleName()).collect(Collectors.toList());
        AuthResp authResp = new AuthResp(
                            user.getIdUser(),
                            user.getUsername(),
                            user.getEmail(),
                            userRoleList,
                            user.getToken());
        return authResp;
    }

    @Override
    public Boolean logout(String token) {
        Optional<User> optional = userRepository.findByTokenAndIsActiveTrue(token);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setToken(null);
            user.setTokenExpired(null);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
