package com.toyboardproject.Service;

import com.toyboardproject.Repository.BoardCommentRepository;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BoardCommentServiceTest {
    @Mock
    private BoardCommentRepository boardCommentRepository;

    @InjectMocks
    private BoardCommentService boardCommentService;

    private BoardCommentRequestDto mockRequestSuccess = BoardCommentRequestDto.builder()
            .id(1L)
            .boardId(1L)
            .commentContent("Test....")
            .build();


    @DisplayName("[Service] 댓글 저장 테스트")
    @Test
    public void createBoardCommentTest() {
        // given
        given(boardCommentRepository.save(any(BoardComment.class)))
                .willReturn(any(BoardComment.class));

        boolean check = boardCommentService.createBoardComment(mockRequestSuccess);

        then(boardCommentRepository).should().save(any(BoardComment.class));

        System.out.println(check);


    }
}