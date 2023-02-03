package com.toyboardproject.config;

import com.toyboardproject.config.SpringSecurityConfig.CustomUserDetailsService;
import com.toyboardproject.config.SpringSecurityConfig.LoginSuccessHandlerCustom;
import com.toyboardproject.config.SpringSecurityConfig.LogoutSuccessHandlerCustrom;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form -> form
                        .loginPage("/account/login")
                        .usernameParameter("userId").passwordParameter("userPassword")
                        .successHandler(new LoginSuccessHandlerCustom())
                        .successForwardUrl("/board/")
                        .permitAll()
                        .failureHandler((request, response, exception) -> {
                            log.info("login fail");
                            response.sendError(401, "로그인에 실패하였습니다.");
                        })
                        .failureUrl("/account/login")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                        .logoutSuccessHandler(new LogoutSuccessHandlerCustrom())
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                ).csrf().disable()
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();


    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
