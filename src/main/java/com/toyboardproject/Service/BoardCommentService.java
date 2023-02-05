package com.toyboardproject.Service;

import com.toyboardproject.domain.BoardComment;
import com.toyboardproject.dto.BoardCommentRequestDto;
import com.toyboardproject.Repository.BoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



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

}
