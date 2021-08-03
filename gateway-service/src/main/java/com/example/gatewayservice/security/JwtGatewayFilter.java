package com.example.gatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Predicate;

@Component
public class JwtGatewayFilter implements GatewayFilter {

    private static final String[] WHITELIST = {
    //AUTH
        "/auth/register",
        "/auth/login"
    };

    @Value("${spring.application.name}")
    private String APPLICATION_NAME;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Predicate<ServerHttpRequest> isSecured = r -> Arrays.stream(WHITELIST).noneMatch(uri -> r.getURI().getPath().contains(uri));
        if (isSecured.test(request)) {
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            String token = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).substring(7);
            try {
                jwtUtils.validateJwtToken(token);
            } catch (RuntimeException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                response.getHeaders().add(HttpHeaders.WARNING, e.getMessage());
                return response.setComplete();
            }
            exchange.getRequest().mutate().header(HttpHeaders.FROM, APPLICATION_NAME).build();
        }
        return chain.filter(exchange);
    }
}
