package com.toyboardproject.repository;

import com.toyboardproject.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(String userId);

    Boolean existsByUserId(String userId);
}
