package com.example.simpleuserfunctionalreactiveserv1.repository;

import com.example.simpleuserfunctionalreactiveserv1.entity.UserEntity;
import com.example.simpleuserfunctionalreactiveserv1.resource.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    Flux<User> findByFirstName(String firstName);

}
