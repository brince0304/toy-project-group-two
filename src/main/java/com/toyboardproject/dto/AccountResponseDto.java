package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
    private String userId;
    private String userPassword;
    private String userNickname;
    private String userName;
    private String userPhoneNum;

    public static AccountResponseDto from(Account entity){
        return AccountResponseDto.builder()
                .userId(entity.getUserId())
                .userPassword(entity.getUserPassword())
                .userNickname(entity.getUserNickname())
                .userName(entity.getUserName())
                .userPhoneNum(entity.getUserPhoneNum())
                .build();
    }
}
