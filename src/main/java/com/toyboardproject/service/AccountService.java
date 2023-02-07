package com.toyboardproject.service;

import com.toyboardproject.domain.Account;
import com.toyboardproject.dto.AccountRequestDto;
import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.utils.BcryptUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    private final BcryptUtil bcryptUtil;



    private void validateDuplicateAccount(String userId) {
        accountRepository.findByUserId(userId).ifPresent(u -> {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        });
    }

    public void createAccount(AccountRequestDto dto) {
        Account account_= accountRepository.save(dto.toEntity());
        account_.setUserPassword(bcryptUtil.encode(account_.getUserPassword()));
    }

    public void deleteAccountById(Long id) {
        if(!accountRepository.existsById(id)){
            throw new EntityNotFoundException("없는 아이디입니다.");
        }
        accountRepository.deleteById(id);
    }

    public void updateAccountById(Long id, final AccountRequestDto dto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 아이디입니다."));
        //각각 설정 할 수 있게
        account.setUserPassword(dto.getUserPassword());
        account.setUserNickname(dto.getUserNickname());
        account.setUserName(dto.getUserName());
        account.setUserPhoneNum(dto.getUserPhoneNum());
    }

    public boolean checkExistUserId(String userId) {
        return !accountRepository.existsByUserId(userId);
    }

    public boolean checkExistUserNickName(String userNickName) {
        return !accountRepository.existsByUserNickname(userNickName);
    }
}
