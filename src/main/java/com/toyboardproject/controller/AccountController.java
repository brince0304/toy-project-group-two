package com.toyboardproject.controller;

import com.toyboardproject.dto.AccountRequestDto;
import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private AccountService accountService;

    private AccountRepository accountRepository;

    @PostMapping("/createAccount")
    public void save(@RequestBody AccountRequestDto dto) {
        accountService.createAccount(dto);
    }
}
