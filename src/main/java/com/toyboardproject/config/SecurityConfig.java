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
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@Slf4j
@AllArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final LoginSuccessHandlerCustom loginSuccessHandlerCustom;

    private final LogoutSuccessHandlerCustrom logoutSuccessHandlerCustrom;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form -> form
                        .loginPage("/account/login")
                        .successHandler(loginSuccessHandlerCustom)
                        .permitAll()
                        .failureHandler((request, response, exception) -> {
                            response.sendError(401, "로그인에 실패하였습니다.");
                        })
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/account/logout"))
                        .logoutSuccessHandler(logoutSuccessHandlerCustrom)
                        .deleteCookies("JSESSIONID")

                ).csrf().disable()
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**","/css/**", "/js/**", "/img/**").permitAll()
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
