package com.hamza.accountserviceaxon.query.repository;

import com.hamza.accountserviceaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
