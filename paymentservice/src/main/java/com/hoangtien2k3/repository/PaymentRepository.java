package com.hoangtien2k3.repository;

import com.hoangtien2k3.data.Payment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveCrudRepository<Payment,Long> {
    @Query("SELECT * FROM payment WHERE account_id = :id")
    Flux<Payment> findByAccountId(String id);
}
