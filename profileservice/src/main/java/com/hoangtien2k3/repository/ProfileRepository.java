package com.hoangtien2k3.repository;

import com.hoangtien2k3.data.Profile;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile,Long> {
//    @Query("SELECT p FROM Profile p WHERE p.email = :email")
    Mono<Profile> findByEmail(String email);
}
