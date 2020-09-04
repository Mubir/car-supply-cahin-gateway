package com.mubir.gateway.car_service_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {
    @Bean
    public RouteLocator loaclHostRoutes(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route(r->r.path("/api/v1/car*","/api/v1/car/*","/api/v1/carUpc/*")
                        .uri("http://localhost:8080").id("carservice"))
                .route(r->r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081").id("order-service"))
                .route(r->r.path("/api/v1/car/*/inventory")
                        .uri("http://localhost:8082").id("inventory-service"))
                .build();
    }
}
