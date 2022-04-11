package com.example.simpleuserfunctionalreactiveserv1.handler.impl;

import com.example.simpleuserfunctionalreactiveserv1.handler.UserHandler;
import com.example.simpleuserfunctionalreactiveserv1.resource.User;
import com.example.simpleuserfunctionalreactiveserv1.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class UserHandlerImpl implements UserHandler {

    private UserService userService;

    private ModelMapper modelMapper;

    public UserHandlerImpl(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.createUser(user), User.class));
    }

    @Override
    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {

        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user ->
                        ServerResponse.status(HttpStatus.NO_CONTENT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(userService.updateUser(id, user), User.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @Override
    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {

        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user ->
                        ServerResponse.status(HttpStatus.NO_CONTENT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(userService.deleteUser(id), User.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {

        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        Mono<User> userMono = userService.getUser(id);
        return userMono.flatMap(user -> ServerResponse.ok()
                        .body(fromValue(user)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
        Optional<String> firstNameOpt = serverRequest.queryParam("first_name");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getUsers(firstNameOpt), User.class);
    }


}
