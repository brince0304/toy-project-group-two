package com.toyboardproject.dto;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
public class BoardRequestDto {

    @NotBlank
    @Size(min = 2, max = 50, message = "제목은 최소 2자 이상 50자 이하로 작성해주세요.")
    private String title;

    @NotBlank
    @Size(min = 10, max = 500, message = "내용은 최소 10자 이상 500자 이하로 작성해주세요.")
    private String content;

    private BoardType boardType;


    @Builder
    public BoardRequestDto(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }
    // dto -> entity
    public Board toEntity(PrincipalDto dto){
        return Board.builder()
                .boardTitle(title)
                .boardContent(content)
                .boardType(boardType)
                .account(dto.toEntity())
                .build();
    }
}
