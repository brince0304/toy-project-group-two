package com.toyboardproject.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BindingCheckAspect {
    @Pointcut("@annotation(com.toyboardproject.Annotation.BindingCheck)")
    public void bindingCheck() {
    }

    @Around("bindingCheck()")
    public Object aroundBindingCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof org.springframework.validation.BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
                }
            }
        }
        return joinPoint.proceed();
    }
}
