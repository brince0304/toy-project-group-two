package com.toyboardproject.service;

import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.repository.BoardCommentRepository;
import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
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


        boolean check = boardCommentService.createBoardComment(mockRequestSuccess,createPrincipalDto());

        then(boardCommentRepository).should().save(any(BoardComment.class));

        System.out.println(check);


    }

    @DisplayName("[Service] 댓글 목록 테스트")
    @Test
    public void getBoardCommentsByBoardIdTest() {
        given(boardCommentRepository.findByBoard(any(Board.class)))
                .willReturn(any());

        Board board = Board.builder().id(1L).build();
        boardCommentRepository.findByBoard(board);

        then(boardCommentRepository).should().findByBoard(board);
        assertThat(board.getId()).isEqualTo(1);


//        List<BoardCommentResponseDto> result = comments.stream().map(comment ->
//                BoardCommentResponseDto.entitiesToDTO(comment)).collect(Collectors.toList());
//
//        for(BoardCommentResponseDto response : result){
//            System.out.println(response.toString());
//        }
    }

    @DisplayName("[Service] 댓글 수정 테스트")
    @Test
    public void updateBoardCommentByCommentId() {
        given(boardCommentRepository.findById(any(Long.class))).willReturn(Optional.of(mockRequestSuccess.toEntity(createPrincipalDto())));

        mockRequestSuccess.setCommentContent("Update Test....");
        boardCommentService.updateBoardComment(mockRequestSuccess);
        then(boardCommentRepository).should().findById(any(Long.class));


    }

    @DisplayName("[Service] 댓글 삭제 성공 테스트")
    @Test
    public void deleteBoardCommentByCommentId() {
        boardCommentRepository.save(mockRequestSuccess.toEntity(createPrincipalDto()));

        boardCommentService.deleteBoardCommentByCommentId(mockRequestSuccess.getId());

        assertThat(boardCommentRepository.count()).isEqualTo(0);
    }

    @DisplayName("[Service] 댓글 삭제 실패 테스트")
    @Test
    public void deleteBoardCommentByCommentIdFail() {
        given(boardCommentRepository.save(any())).willReturn(any());

        boardCommentService.createBoardComment(mockRequestSuccess,createPrincipalDto());

        assertThatThrownBy(() -> boardCommentService.deleteBoardCommentByCommentId(3L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private PrincipalDto createPrincipalDto(){
        return PrincipalDto.builder()
                .id(1L)
                .userId("test")
                .userPassword("test")
                .userName("name")
                .build();
    }

}