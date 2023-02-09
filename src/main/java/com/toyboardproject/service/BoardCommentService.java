package com.toyboardproject.service;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.repository.BoardCommentRepository;
import com.toyboardproject.dto.BoardCommentResponseDto;
import com.toyboardproject.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;

    // 댓글 저장 로직
    public boolean createBoardComment(BoardCommentRequestDto boardCommentRequestDto,
                                      PrincipalDto principal){
        if(boardRepository.findById(boardCommentRequestDto.getBoardId()).isEmpty())
            throw new EntityNotFoundException("존재하지 않는 게시글입니다.");

        BoardComment comment = boardCommentRequestDto.toEntity(principal);

        boardCommentRepository.save(comment);

        return true;
    }

    // 댓글 목록 반환 로직
    public List<BoardCommentResponseDto> getBoardCommentsByBoardId(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        List<BoardComment> comments = boardCommentRepository.findByBoard(board);

        return comments.stream().map(BoardCommentResponseDto::from).collect(Collectors.toList());
    }

    // 댓글 수정 로직
    public void updateBoardComment(BoardCommentRequestDto boardCommentRequestDto){
        BoardComment result = boardCommentRepository.findById(boardCommentRequestDto.getId())
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        result.setCommentContent(boardCommentRequestDto.getCommentContent());

        boardCommentRepository.save(result);
    }

    // 댓글 삭제 로직
    public void deleteBoardCommentByCommentId(Long id){
        BoardComment comment = boardCommentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 댓글입니다."));
        boardCommentRepository.deleteById(id);
    }

    public boolean isBoardCommentOwner(Long id, PrincipalDto principal) {
        BoardComment comment = boardCommentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        return comment.getAccount().getId().equals(principal.getId());
    }
}
