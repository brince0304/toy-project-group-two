package com.toyboardproject.controller;



import com.toyboardproject.Annotation.AuthCheck;
import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.Annotation.BoardAuthorCheck;
import com.toyboardproject.Annotation.BoardCommentAuthorCheck;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.dto.BoardCommentResponseDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardCommentService;
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

    @BindingCheck
    @AuthCheck
    @PostMapping("/comment")
    public ResponseEntity<Boolean> createComment(@AuthenticationPrincipal PrincipalDto principal,
                                                 @Valid @RequestBody BoardCommentRequestDto commentRequest,
                                                 BindingResult bindingResult){
        boolean result = boardCommentService.createBoardComment(commentRequest, principal);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 댓글 목록 불러오기
     * */
    @AuthCheck
    @GetMapping("/comment/{boardId}")
    public ResponseEntity<List<BoardCommentResponseDto>> getList(@PathVariable Long boardId){
        List<BoardCommentResponseDto> commentList = boardCommentService.getBoardCommentsByBoardId(boardId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    /**
     * 댓글 수정 기능
     */
    @BindingCheck
    @AuthCheck
    @BoardCommentAuthorCheck
    @PutMapping("/comment")
    public ResponseEntity<Boolean> updateComment(@AuthenticationPrincipal PrincipalDto principal,
                                                  @Valid @RequestBody BoardCommentRequestDto dto,
                                                  BindingResult bindingResult){
        boardCommentService.updateBoardComment(dto);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * 댓글 삭제 기능
     */
    @AuthCheck
    @BoardCommentAuthorCheck
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@AuthenticationPrincipal PrincipalDto principal,
                                                 @PathVariable Long commentId){
        boardCommentService.deleteBoardCommentByCommentId(commentId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
