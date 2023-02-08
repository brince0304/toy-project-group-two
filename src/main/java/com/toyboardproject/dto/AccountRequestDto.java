package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountRequestDto {
    private String userId;

    @Size(min=8, max=64 , message="최소 8자에서 최대 64자로 입력해주세요")
    private String userPassword;

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String userNickname;

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String userName;

    @Size(min=7, max=12 , message="최소 7자에서 최대 12자로 입력해주세요")
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
