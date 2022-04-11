package com.example.simpleuserfunctionalreactiveserv1.service.impl;

import com.example.simpleuserfunctionalreactiveserv1.entity.UserEntity;
import com.example.simpleuserfunctionalreactiveserv1.repository.UserRepository;
import com.example.simpleuserfunctionalreactiveserv1.resource.User;
import com.example.simpleuserfunctionalreactiveserv1.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // flatMap - asynchronous
    // map - synchronous
    // No difference in blocking/non-blocking when used Mono<User> or User as input
    public Mono<User> createUser(Mono<User> userMono){
        return userMono.flatMap(user -> {
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            return userRepository.save(userEntity).log().map(savedUserEntity -> modelMapper.map(savedUserEntity, User.class));
        });
    }

    public Mono<User> createUser(User user){
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        return userRepository.save(userEntity).log().map(savedUserEntity -> modelMapper.map(savedUserEntity, User.class));
    }

    public Mono<Void> updateUser(long id, User user){
        return userRepository.findById(id)
                .flatMap(userEntity -> {
                    modelMapper.map(user, userEntity);
                    return userRepository.save(userEntity).log().then();
                });
    }

    public Mono<Void> deleteUser(long id){
        return userRepository.deleteById(id);
    }

    public Flux<User> getUsers(Optional<String> firstNameOpt){
        if(firstNameOpt.isPresent()) {
            return userRepository.findByFirstName(firstNameOpt.get()).log().map(userEntity -> modelMapper.map(userEntity, User.class));
        } else {
            return userRepository.findAll().log().map(userEntity -> modelMapper.map(userEntity, User.class));
        }
    }

    public Mono<User> getUser(long id){
        return userRepository.findById(id).log().map(userEntity -> modelMapper.map(userEntity, User.class));
    }


}
