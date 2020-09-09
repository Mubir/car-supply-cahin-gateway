package com.mubir.gateway.car_service_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LocalBalancedRoutesConfig {
    @Bean
    public RouteLocator loaclBanlancedRoutes(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route(r->r.path("/api/v1/car*","/api/v1/car/*","/api/v1/carUpc/*")
                        .uri("lb://car-service").id("car-service"))
                .route(r->r.path("/api/v1/customers/**")
                        .uri("lb://order-service").id("order-service"))
                .route(r->r.path("/api/v1/car/*/inventory")
                        .filters(f->f.circuitBreaker(c->c.setName("inventoryCB")
                                .setFallbackUri("forward://inventory-failover").setRouteId("inventory_fail")))
                        .uri("lb://inventory-service").id("inventory-service"))
                .route(r->r.path("/inventory-failover/**")
                .uri("lb://inventory-failover").id("inventory-failover-service"))
                .build();
    }
}
