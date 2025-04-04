package com.sofka.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        GatewayFilter gatewayFilter = jwtFilter.apply(jwtFilter.newConfig());
        return builder.routes()
                .route("auth_service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(gatewayFilter))
                        .uri("lb://auth-service"))
                .route("account_service", r -> r.path("/account/**", "/movement/**")
                        .filters(f -> f.filter(gatewayFilter))
                        .uri("lb://account-service"))
                .build();
    }
}
