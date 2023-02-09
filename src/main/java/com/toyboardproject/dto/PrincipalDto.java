package com.toyboardproject.dto;

import com.toyboardproject.domain.Account;
import com.toyboardproject.domain.AccountRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Data
public class PrincipalDto implements UserDetails {
    private Long id;
    private String userId;
    private String userPassword;
    private String userName;
    private String userPhone;

    private String userNickname;
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account toEntity(){
        return Account.builder()
                .id(id)
                .userId(userId)
                .userPassword(userPassword)
                .userName(userName)
                .userPhoneNum(userPhone)
                .userNickname(userNickname)
                .build();
    }
}
