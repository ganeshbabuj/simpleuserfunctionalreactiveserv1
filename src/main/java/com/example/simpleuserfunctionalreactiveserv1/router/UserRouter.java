package com.example.simpleuserfunctionalreactiveserv1.router;

import com.example.simpleuserfunctionalreactiveserv1.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Slf4j
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {

        return RouterFunctions.nest(path("/v1/account").and(accept(APPLICATION_JSON)),
                RouterFunctions.route(POST("/users"), userHandler::createUser)
                        .andRoute(PUT("/users/{id}"), userHandler::createUser)
                        .andRoute(DELETE("/users/{id}"), userHandler::createUser)
                        .andRoute(GET("/users/{id}"), userHandler::getUser)
                        .andRoute(GET("/users"), userHandler::getUsers)
        );
    }

}