package com.toyboardproject.Service;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.Repository.BoardCommentRepository;
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

    // 댓글 수정 로직
    public Boolean updateBoardComment(BoardCommentRequestDto boardCommentRequestDto){
        Optional<BoardComment> result = boardCommentRepository.findById(boardCommentRequestDto.getId());

        if(result.isPresent()){
            BoardComment comment = result.get();
            comment.changeCommentContent(boardCommentRequestDto.getCommentContent());

            boardCommentRepository.save(comment);

            return true;
        }

        return false;
    }

    // 댓글 삭제 로직
    public Boolean deleteBoardCommentByCommentId(Long id){
        Optional<BoardComment> comment = boardCommentRepository.findById(id);
        if(comment.isEmpty()){
            throw new EntityNotFoundException("오류가 발생하였습니다.");
        }

        boardCommentRepository.deleteById(id);
        return true;
    }
}
