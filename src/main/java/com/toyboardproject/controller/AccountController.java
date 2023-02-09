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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/account/check")
    public ResponseEntity<?> checkExist(@RequestParam (required = false) String userId,
                                        @RequestParam (required = false) String userNickname) {
        if(userId!=null){
           return new ResponseEntity<>(accountService.checkExistUserId(userId),HttpStatus.OK);
        }
        if(userNickname!=null){
            return new ResponseEntity<>(accountService.checkExistUserNickname(userNickname),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/account/sign-up")
    public ModelAndView getSingUpPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account/sign-up");
        return mv;
    }
}
