package com.toyboardproject.Aspect;

import com.toyboardproject.dto.PrincipalDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuthCheckAspect {
    @Pointcut("@annotation(com.toyboardproject.Annotation.AuthCheck)")
    public void authCheck() {
    }

    @Before("authCheck()&&args(principal,..)")
    public void beforeAuthCheck(PrincipalDto principal) throws IllegalAccessException {
        if (principal == null) {
            throw new IllegalAccessException("로그인이 필요합니다.");
        }
    }
}
