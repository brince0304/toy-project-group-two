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

    private Set<BoardComment> boardComments;

    private LocalDateTime modifiedAt;

    private String userNickname;


    public BoardResponseDto(Board entity){
        this.title = entity.getBoardTitle();
        this.content = entity.getBoardContent();
    }
}
