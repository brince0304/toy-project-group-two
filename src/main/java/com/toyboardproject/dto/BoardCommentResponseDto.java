package com.toyboardproject.dto;

import com.toyboardproject.domain.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardCommentResponseDto {
    private Long id;
    private Long boardId;
    private String userId;
    private String commentContent;
    private String userNickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardCommentResponseDto from(BoardComment boardComment){
        return BoardCommentResponseDto.builder()
                .id(boardComment.getId())
                .boardId(boardComment.getBoard().getId())
                .userId(boardComment.getAccount().getUserId())
                .commentContent(boardComment.getCommentContent())
                .userNickname(boardComment.getAccount().getUserNickname())
                .createdAt(boardComment.getCreatedAt())
                .modifiedAt(boardComment.getModifiedAt())
                .build();

    }
}
