package com.toyboardproject.Aspect;

import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.exception.NotAuthorizedException;
import com.toyboardproject.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class BoardCommentAuthorCheck {
    private final BoardCommentService boardCommentService;

    @Pointcut("@annotation(com.toyboardproject.Annotation.BoardCommentAuthorCheck)")
    public void boardCommentAuthorCheck() {
    }

    @Before("boardCommentAuthorCheck()")
    public void beforeBoardCommentAuthorCheck(JoinPoint jp) throws IllegalAccessException {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if(arg instanceof BoardCommentRequestDto boardCommentRequestDto){
                if(!boardCommentService.isBoardCommentOwner(boardCommentRequestDto.getId(),(PrincipalDto)args[0])){
                    throw new NotAuthorizedException("권한이 없습니다.");
                }
            }
            if(arg instanceof Long id){
                if(!boardCommentService.isBoardCommentOwner(id,(PrincipalDto)args[0])){
                    throw new NotAuthorizedException("권한이 없습니다.");
                }
            }
        }
    }

}
