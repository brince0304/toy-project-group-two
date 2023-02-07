package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountRequestDto {
    private String userId;
    private String userPassword;
    private String userNickname;
    private String userName;
    private String userPhoneNum;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userName(userName)
                .userPhoneNum(userPhoneNum)
                .build();
    }
}
