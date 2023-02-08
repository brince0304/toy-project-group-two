package com.toyboardproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyboardproject.config.SecurityConfig;
import com.toyboardproject.dto.BoardCommentRequestDto;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
class BoardCommentControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @DisplayName("[Controller] 댓글 저장 성공 테스트")
    @WithUserDetails("test")
    @Test
    public void createBoardCommentTest() throws Exception {
        BoardCommentRequestDto request = BoardCommentRequestDto.builder()
                .commentContent("test3......")
                .boardId(1L)
                .build();

        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @DisplayName("[Controller] 댓글 목록 성공 테스트")
    @Test
    public void getListTest() throws Exception{
        mockMvc.perform(get("/comment/")
                        .param("boardId", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @WithUserDetails("test")
    @DisplayName("[Controller] 댓글 수정 성공 테스트")
    @Test
    public void updateComment() throws Exception {
        BoardCommentRequestDto request = BoardCommentRequestDto.builder()
                .commentContent("Update test6.......")
                .boardId(1L)
                .id(8L)
                .build();

        mockMvc.perform(put("/comment/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

    }


    @Disabled
    @WithUserDetails("test")
    @DisplayName("[Controller] 댓글 삭제 성공 테스트")
    @Test
    public void deleteComment() throws Exception{
        mockMvc.perform(delete("/comment/")
                        .param("commentId", String.valueOf(6))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}