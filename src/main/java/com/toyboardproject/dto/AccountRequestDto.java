package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private String userId;

    @Size(min=8, max=15 , message="비밀번호는 최소 8자에서 최대 15자로 입력해주세요")
    private String userPassword;

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String userNickname;

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String userName;

    @Size(min=7, max=12 , message="최소 10자에서 최대 11자로 입력해주세요")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "휴대폰 번호 형식이 아닙니다.")
    private String userPhoneNum;

    private Boolean isAgreed;

    public Account toEntity() {
        return Account.builder()
                .userId(userId)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .userName(userName)
                .userPhoneNum(userPhoneNum)
                .isAgreed(isAgreed)
                .build();
    }
}
