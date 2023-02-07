package com.toyboardproject.dto;


import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardCommentRequestDto {
    private Long id;
    private Long boardId;
    @Size(min = 2, max = 50, message = "댓글은 최소 2자 이상 50자 이하로 작성해주세요.")
    private String commentContent;

    // DTO -> Entity 객체로 변환
    public BoardComment dtoToEntity(){
        return BoardComment.builder()
                .board(Board.builder().id(boardId).build())
                .commentContent(commentContent)
                .build();
    }

}
