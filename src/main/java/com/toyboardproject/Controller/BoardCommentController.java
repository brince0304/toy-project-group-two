package com.toyboardproject.Controller;


import com.toyboardproject.Annotation.AuthCheck;
import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.Service.BoardCommentService;
import com.toyboardproject.dto.BoardCommentResponseDto;
import com.toyboardproject.dto.PrincipalDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    /**
     * 댓글 저장 기능
     * */
    @AuthCheck
    @BindingCheck
    @PostMapping("/comment")
    private ResponseEntity<Boolean> createComment(@AuthenticationPrincipal PrincipalDto principalDto,
            @Valid @RequestBody BoardCommentRequestDto commentRequest,
                                                  BindingResult bindingResult){
        log.info("댓글 저장 기능 수행");
        boolean result = boardCommentService.createBoardComment(commentRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 댓글 목록 불러오기
     * */
    @GetMapping("/comment/")
    private ResponseEntity<List<BoardCommentResponseDto>> getList(Long boardId){
        log.info("댓글 목록 불러오기");
        List<BoardCommentResponseDto> commentList = boardCommentService.getBoardCommentsByBoardId(boardId);

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    /**
     * 댓글 수정 기능
     */
    @BindingCheck
    @AuthCheck
    @PutMapping("/comment/")
    private ResponseEntity<Boolean> updateComment(@AuthenticationPrincipal PrincipalDto principalDto,
            @Valid @RequestBody BoardCommentRequestDto boardCommentRequestDto,
                                                  BindingResult bindingResult){
        log.info("댓글 수정 기능 수행");
        boolean result = boardCommentService.updateBoardComment(boardCommentRequestDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
