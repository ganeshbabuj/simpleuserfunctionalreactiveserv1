package com.example.simpleuserfunctionalreactiveserv1.handler;

import com.example.simpleuserfunctionalreactiveserv1.repository.UserRepository;
import com.example.simpleuserfunctionalreactiveserv1.resource.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface UserHandler {

    Mono<ServerResponse> createUser(ServerRequest serverRequest);
    Mono<ServerResponse> updateUser(ServerRequest serverRequest);
    Mono<ServerResponse> deleteUser(ServerRequest serverRequest);
    Mono<ServerResponse> getUser(ServerRequest serverRequest);
    Mono<ServerResponse> getUsers(ServerRequest serverRequest);

}