package com.toyboardproject.Controller;


import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.Service.BoardCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    /**
     * 댓글 저장 기능
     * */
    @PostMapping("/comment")
    private ResponseEntity<Boolean> createComment(@Valid @RequestBody BoardCommentRequestDto commentRequest,
                                                  BindingResult bindingResult){
        log.info("댓글 저장 기능 수행");
        boolean result = boardCommentService.createBoardComment(commentRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
