package com.toyboardproject.domain.account.service;

public interface AccountService {
    boolean createAccount(AccountSignUpRequest accountSignUpRequest) throws Exception;

    boolean checkExistUserId(String userId) throws Exception;

    boolean checkExistUserNick(String userNickname) throws Exception;
}
