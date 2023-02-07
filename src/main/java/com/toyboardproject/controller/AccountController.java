package com.toyboardproject.controller;

import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.dto.AccountRequestDto;
import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;


    @PostMapping("/account/sign-up")
    @BindingCheck
    public ResponseEntity<?> save(@RequestBody @Valid AccountRequestDto dto, BindingResult bindingResult) {
        accountService.createAccount(dto);
        return new ResponseEntity<>(dto.getUserId(), HttpStatus.OK);
    }

    @GetMapping("/account/login")
    public ModelAndView getLoginPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account/login");
        return mv;
    }

    @GetMapping("/account/sign-up")
    public ModelAndView getSingUpPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account/sign-up");
        return mv;
    }
}
