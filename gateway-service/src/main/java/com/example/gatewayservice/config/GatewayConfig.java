package com.example.gatewayservice.config;

import com.example.gatewayservice.security.JwtGatewayFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private static final String PATH_DEFAULT = "/api-gateway/";

    @Autowired
    JwtGatewayFilter jwtGatewayFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                //SYSTEM-SERVICE
                .route("authRoute",
                        r -> r.path(PATH_DEFAULT + "auth/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "auth", "/auth")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://system-service:8003"))
                .route("userRoute",
                        r -> r.path(PATH_DEFAULT + "user/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "user", "/user")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://system-service:8003"))
                .route("roleRoute",
                        r -> r.path(PATH_DEFAULT + "role/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "role", "/role")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://system-service:8003"))
                .route("userRoleRoute",
                        r -> r.path(PATH_DEFAULT + "user-role/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "user-role", "/user-role")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://system-service:8003"))

                //PRODUCT-SERVICE
                .route("productRoute",
                        r -> r.path(PATH_DEFAULT + "product/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "product", "/product")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://product-service:8004"))

                //ORDER-SERVICE
                .route("orderRoute",
                        r -> r.path(PATH_DEFAULT + "order/**")
                                .filters(f -> f
                                        .rewritePath(PATH_DEFAULT + "order", "/order")
                                        .filter(jwtGatewayFilter))
                                .uri("lb://order-service:8005"))
                .build();
    }
}
