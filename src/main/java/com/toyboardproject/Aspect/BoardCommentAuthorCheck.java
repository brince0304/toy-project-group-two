package com.toyboardproject.Aspect;

import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BoardCommentAuthorCheck {
    private final BoardCommentService boardCommentService;

    @Pointcut("@annotation(com.toyboardproject.Annotation.BoardCommentAuthorCheck)")
    public void boardCommentAuthorCheck() {
    }

    @Before(value = "boardCommentAuthorCheck()&&args(principal,id,dto,..)", argNames = "principal,id,dto")
    public void beforeBoardCommentAuthorCheck(PrincipalDto principal, Long id , BoardCommentRequestDto dto) throws IllegalAccessException {
        if (id!=null&&!boardCommentService.isBoardCommentOwner(id, principal)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }
        if(dto!=null&&!boardCommentService.isBoardCommentOwner(dto.getId(),principal)){
            throw new IllegalAccessException("권한이 없습니다.");
        }
    }

}
