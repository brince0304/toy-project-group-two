package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import lombok.Data;

@Data
public class AccountResponseDto {
    private String userId;
    private String userPassword;
    private String userNickname;
    private String userName;
    private String userPhoneNum;

    public AccountResponseDto(Account account) {
        this.userId = account.getUserId();
        this.userPassword = account.getUserPassword();
        this.userNickname = account.getUserNickname();
        this.userName = account.getUserName();
        this.userPhoneNum = account.getUserPhoneNum();
    }
}
