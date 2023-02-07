package com.toyboardproject.service;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.repository.BoardCommentRepository;
import com.toyboardproject.dto.BoardCommentResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    // 댓글 저장 로직
    public boolean createBoardComment(BoardCommentRequestDto boardCommentRequestDto, PrincipalDto principal){
        BoardComment comment = boardCommentRequestDto.toEntity(principal);

        boardCommentRepository.save(comment);

        return true;
    }

    // 댓글 목록 반환 로직
    public List<BoardCommentResponseDto> getBoardCommentsByBoardId(Long boardId){
        Board board = Board.builder().id(boardId).build();

        List<BoardComment> comments = boardCommentRepository.findByBoard(board);

        return comments.stream().map(BoardCommentResponseDto::from).collect(Collectors.toList());
    }

    // 댓글 수정 로직
    public void updateBoardComment(BoardCommentRequestDto boardCommentRequestDto){
        BoardComment result = boardCommentRepository.findById(boardCommentRequestDto.getId()).orElseThrow(()-> new EntityNotFoundException("없는 댓글입니다."));
        result.setCommentContent(boardCommentRequestDto.getCommentContent());
    }

    // 댓글 삭제 로직
    public void deleteBoardCommentByCommentId(Long id){
        BoardComment comment = boardCommentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 댓글입니다."));
        boardCommentRepository.deleteById(id);
    }
}
