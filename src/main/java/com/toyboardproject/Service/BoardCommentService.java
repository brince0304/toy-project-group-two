package com.toyboardproject.Service;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.Repository.BoardCommentRepository;
import com.toyboardproject.dto.BoardCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    // 댓글 저장 로직
    public boolean createBoardComment(BoardCommentRequestDto boardCommentRequestDto){
        BoardComment comment = boardCommentRequestDto.dtoToEntity();

        boardCommentRepository.save(comment);

        return true;
    }

    // 댓글 목록 반환 로직
    public List<BoardCommentResponseDto> getBoardCommentsByBoardId(Long boardId){
        Board board = Board.builder().id(boardId).build();

        List<BoardComment> comments = boardCommentRepository.findByBoard(board);

        return comments.stream().map(comment->
                BoardCommentResponseDto.entitiesToDTO(comment)).collect(Collectors.toList());
    }




}
