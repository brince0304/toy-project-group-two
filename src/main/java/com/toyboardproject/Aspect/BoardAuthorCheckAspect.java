package com.toyboardproject.Aspect;

import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.exception.NotAuthorizedException;
import com.toyboardproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BoardAuthorCheckAspect {
    private final BoardService boardService;

    @Pointcut("@annotation(com.toyboardproject.Annotation.BoardAuthorCheck)")
    public void boardAuthorCheck() {
    }

    @Before("boardAuthorCheck()")
    public void beforeBoardAuthorCheck(JoinPoint jp) throws IllegalAccessException {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if(arg instanceof Long id){
                if(!boardService.isBoardOwner(id,(PrincipalDto)args[0])){
                    throw new NotAuthorizedException("권한이 없습니다.");
                }
            }
        }
    }

}
