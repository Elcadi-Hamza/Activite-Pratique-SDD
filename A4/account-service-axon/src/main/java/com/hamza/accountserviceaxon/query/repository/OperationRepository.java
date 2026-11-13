package com.hamza.accountserviceaxon.query.repository;

import com.hamza.accountserviceaxon.query.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByAccountId(String id);
}
