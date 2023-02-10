package com.toyboardproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyboardproject.config.SecurityConfig;
import com.toyboardproject.controller.BoardController;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.dto.BoardRequestDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @DisplayName("[Controller] 게시글 생성 성공 테스트 - 자유")
    @WithUserDetails("test")
    @Test
    public void createBoardFree() throws Exception {
        BoardRequestDto request = BoardRequestDto.builder()
                .title("제목입니다")
                .content("내용은 10자이상 적어주세요")
                .boardType(BoardType.FREE)
                .build();

        mockMvc.perform(post("/board/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @DisplayName("[Controller] 게시글 생성 성공 테스트 - 공지")
    @WithUserDetails("test")
    @Test
    public void createBoardNotice() throws Exception {
        BoardRequestDto request = BoardRequestDto.builder()
                .title("제목입니다")
                .content("내용은 10자이상 적어주세요")
                .boardType(BoardType.NOTICE)
                .build();

        mockMvc.perform(post("/board/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @DisplayName("[Controller] 게시글 생성 성공 테스트 - 질문")
    @WithUserDetails("test")
    @Test
    public void createBoardFaq() throws Exception {
        BoardRequestDto request = BoardRequestDto.builder()
                .title("제목입니다")
                .content("내용은 10자이상 적어주세요")
                .boardType(BoardType.FAQ_USE)
                .build();

        mockMvc.perform(post("/board/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @DisplayName("[Controller] 게시글 생성 실패 테스트 - 공백일경우")
    @WithUserDetails("test")
    @Test
    public void createBoardBlankError() throws Exception {
        BoardRequestDto request = BoardRequestDto.builder()
                .title("    ")
                .content("             ")
                .boardType(BoardType.FREE)
                .build();

        mockMvc.perform(post("/board/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andDo(print());

    }

    @DisplayName("[Controller] 게시글 생성 실패 테스트 - 글자수에러")
    @WithUserDetails("test")
    @Test
    public void createBoardTextError() throws Exception {
        BoardRequestDto request = BoardRequestDto.builder()
                .title("제")
                .content("내용 10자 안됨")
                .boardType(BoardType.FREE)
                .build();

        mockMvc.perform(post("/board/post")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andDo(print());

    }
/////////////////////////////////////////////////
    @WithUserDetails("test")
    @DisplayName("[Controller] 게시글 수정 성공 테스트")
    @Test
    public void updateBoardById() throws Exception {
        BoardRequestDto requestDto = BoardRequestDto.builder()
                .title("제목수정")
                .content("내용도 수정하는데 되니?")
                .boardType(BoardType.NOTICE)
                .build();


        mockMvc.perform(put("/board/1")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithUserDetails("test")
    @DisplayName("[Controller] 게시글 수정 실패 테스트 - 게시글 없을때")
    @Test
    public void updateBoardByIdError() throws Exception {
        BoardRequestDto requestDto = BoardRequestDto.builder()
                .title("제목수정")
                .content("내용도 수정하는데 되니?")
                .boardType(BoardType.NOTICE)
                .build();

        mockMvc.perform(put("/board/6")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andDo(print());

    }

    @WithUserDetails("test2")
    @DisplayName("[Controller] 게시글 수정 실패 테스트 - 본인이 작성하지 않은 글 수정 시도")
    @Test
    public void updateBoardByIdError2() throws Exception {
        BoardRequestDto requestDto = BoardRequestDto.builder()
                .title("제목수정")
                .content("내용도 수정하는데 되니?")
                .boardType(BoardType.NOTICE)
                .build();

        mockMvc.perform(put("/board/9")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/?type=FREE"))
                .andDo(print());
    }
//////////////////////
    @WithUserDetails("test")
    @DisplayName("[Controller] 게시글 삭제 성공 테스트")
    @Test
    public void deleteBoardById() throws Exception {

        mockMvc.perform(delete("/board/1")
                ).andExpect(status().isOk())
                .andDo(print());
    }

    @WithUserDetails("test")
    @DisplayName("[Controller] 게시글 삭제 실패 테스트 - 없는 게시글 삭제 시도")
    @Test
    public void deleteBoardByIdError() throws Exception {

        mockMvc.perform(delete("/board/7")
                        .content("{ \"boardId\" : 7}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andDo(print());
    }

    @WithUserDetails("test2")
    @DisplayName("[Controller] 게시글 삭제 실패 테스트 - 본인이 작성하지 않은 글 삭제 시도")
    @Test
    public void deleteBoardByIdError2() throws Exception {

        mockMvc.perform(delete("/board/9")
                        .content("{ \"boardId\" : 9}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/?type=FREE"))
                .andDo(print());
    }


}

