package com.toyboardproject.service;

import com.toyboardproject.domain.Account;
import com.toyboardproject.dto.AccountRequestDto;
import com.toyboardproject.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private void validateDuplicateAccount(String userId) {
        accountRepository.findByUserId(userId).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        });
    }

    public void createAccount(AccountRequestDto dto) {
        accountRepository.save(dto.toEntity());
    }

    public void deleteAccountById(Long id) {
        if(!accountRepository.existsById(id)){
            throw new EntityNotFoundException("없는 아이디입니다.");
        }
        accountRepository.deleteById(id);
    }

    public void updateAccountById(Long id, final AccountRequestDto dto) {
        Optional<Account> account = accountRepository.findById(id);

        Account account1 = account.get();
        account1.setUserId(dto.getUserId());
        account1.setUserPassword(dto.getUserPassword());
        account1.setUserNickname(dto.getUserNickname());
        account1.setUserName(dto.getUserName());
        account1.setUserPhoneNum(dto.getUserPhoneNum());
        accountRepository.save(account1);
    }

    public boolean checkExistUserId(String userId) {
        if(accountRepository.existsByUserId(userId)) {
            return false;
        }
        return true;
    }

    public boolean checkExistUserNickName(String userNickName) {
        if (accountRepository.existsByUserNickName(userNickName)) {
            return false;
        }
        return true;
    }
}
