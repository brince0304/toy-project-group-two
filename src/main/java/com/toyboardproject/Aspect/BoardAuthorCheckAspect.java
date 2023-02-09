package com.toyboardproject.Aspect;

import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardService;
import lombok.RequiredArgsConstructor;
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

    @Before(value = "boardAuthorCheck()&&args(principal,id,..)", argNames = "principal,id")
    public void beforeBoardAuthorCheck(PrincipalDto principal,Long id ) throws IllegalAccessException {
        if (!boardService.isBoardOwner(id, principal)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }
    }


}
