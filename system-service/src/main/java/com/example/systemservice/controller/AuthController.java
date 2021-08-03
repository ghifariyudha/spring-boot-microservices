package com.example.systemservice.controller;

import com.example.systemservice.domain.entity.User;
import com.example.systemservice.payload.request.AuthReq;
import com.example.systemservice.payload.response.AuthResp;
import com.example.systemservice.repository.UserRepository;
import com.example.systemservice.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Auth")
@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    AuthService service;
    @Autowired
    UserRepository userRepository;

    @PostMapping("register")
    @ApiOperation(value = "Register")
    @ApiImplicitParams(
            @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, required = false, paramType = "header", dataType = "string")
    )
    public ResponseEntity<Object> register(@Valid @RequestBody User req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        User user = service.register(req);
        return ResponseEntity.ok(user);
    }

    @PostMapping("login")
    @ApiOperation(value = "Login")
    @ApiImplicitParams(
            @ApiImplicitParam(name = HttpHeaders.AUTHORIZATION, required = false, paramType = "header", dataType = "string")
    )
    public ResponseEntity<Object> login(@Valid @RequestBody AuthReq req) {
        AuthResp authResp = service.login(req);
        return ResponseEntity.ok(authResp);
    }

    @PostMapping("logout")
    @ApiOperation(value = "Logout")
    public ResponseEntity<Object> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        Boolean success = service.logout(token.substring(7));
        return success ?
                ResponseEntity.status(HttpStatus.OK).body("You have been successfully logged out!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed logged out");
    }
}
