package com.toyboardproject.dto;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class BoardDto {

    private String title;

    private String content;

    private BoardType boardType;

    @Builder
    public BoardDto(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }
    public Board toEntity(PrincipalDto dto){
        return Board.builder()
                .boardTitle(title)
                .boardContent(content)
                .boardType(boardType);
                .account(dto.toEntity())
                .build();
    }
}
