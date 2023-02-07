package com.toyboardproject.config.SpringSecurityConfig;

import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.domain.Account;
import com.toyboardproject.dto.PrincipalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account_ = accountRepository.findByUserId(username).orElseThrow(()->new UsernameNotFoundException("계정이 존재하지 않습니다."));

        return PrincipalDto.builder()
                .userId(account_.getUserId())
                .userName(account_.getUserName())
                .userPassword(account_.getUserPassword())
                .userNickname(account_.getUserNickname())
                .userPhone(account_.getUserPhoneNum()).build();

    }
}
