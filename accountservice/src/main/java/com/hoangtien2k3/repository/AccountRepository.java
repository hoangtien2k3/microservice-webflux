package com.hoangtien2k3.repository;

import com.hoangtien2k3.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountRepository extends ReactiveCrudRepository<Account,String> {

}
