package com.toyboardproject.Repository;

import com.toyboardproject.domain.Account;

import java.util.Optional;

public interface AccountRepository extends org.springframework.data.jpa.repository.JpaRepository<Account, String> {
    Optional<Account> findByUserId(String userId);
}
