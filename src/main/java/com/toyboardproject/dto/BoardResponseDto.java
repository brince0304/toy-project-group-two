package com.toyboardproject.dto;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {

    private Long id;
    
    private String title;
    
    private String content;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime modifiedAt;

    private String userNickname;
    
    private String userId;
    
    private Long commentCount;
    
    public static BoardResponseDto from(Board entity){
        return BoardResponseDto.Builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .content(entity.getContent())
        .createdAt(entity.getCreatedAt())
        .modifiedAt(entity.getModifiedAt())
        .userNickname(entity.getAccount().getUserNickname())
        .userId(entity.getAccount().getUserId())
        .commentCount(entity.getBoardComments().count())
        .build();
    }
}
