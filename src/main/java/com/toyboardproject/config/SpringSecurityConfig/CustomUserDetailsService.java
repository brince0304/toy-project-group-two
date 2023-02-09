package com.toyboardproject.config.SpringSecurityConfig;

import com.toyboardproject.domain.AccountRole;
import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.domain.Account;
import com.toyboardproject.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account_ = accountRepository.findByUserId(username).orElseThrow(()->new UsernameNotFoundException("계정이 존재하지 않습니다."));
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (AccountRole role : account_.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getValue()));
        }
        return PrincipalDto.builder()
                .id(account_.getId())
                .userId(account_.getUserId())
                .userName(account_.getUserName())
                .userPassword(account_.getUserPassword())
                .userNickname(account_.getUserNickname())
                .authorities(authorities)
                .userPhone(account_.getUserPhoneNum()).build();


    }
}
