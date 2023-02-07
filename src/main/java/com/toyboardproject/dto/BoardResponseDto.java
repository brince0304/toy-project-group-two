package com.toyboardproject.dto;

import com.toyboardproject.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    
    private Integer commentCount;
    
    public static BoardResponseDto from(Board entity){
        return BoardResponseDto.builder()
        .id(entity.getId())
        .title(entity.getBoardTitle())
        .content(entity.getBoardContent())
        .createdAt(entity.getCreatedAt())
        .modifiedAt(entity.getModifiedAt())
        .userNickname(entity.getAccount().getUserNickname())
        .userId(entity.getAccount().getUserId())
        .commentCount(entity.getBoardComments().size())
        .build();
    }
}
