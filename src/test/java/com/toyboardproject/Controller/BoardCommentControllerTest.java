package com.toyboardproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyboardproject.repository.AccountRepository;
import com.toyboardproject.repository.BoardRepository;
import com.toyboardproject.service.BoardCommentService;
import com.toyboardproject.config.SecurityConfig;
import com.toyboardproject.domain.Account;
import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
                .commentContent("test2......")
                .boardId(1L)
                .build();

        mockMvc.perform(post("/comment")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
//
//        boolean check = commentService.createBoardComment(request);
//        System.out.println(check);
    }


    @DisplayName("[Controller] 댓글 목록 성공 테스트")
    @Test
    public void getListTest() throws Exception{


        mockMvc.perform(get("/comment/")
                        .content("{ \"boardId\" : 1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());


//        List<BoardCommentResponseDto> result = commentService.getBoardCommentsByBoardId(1L);
//
//        for(BoardCommentResponseDto response : result){
//            System.out.println(response.toString());
//        }
    }

    @WithUserDetails("test")
    @DisplayName("[Controller] 댓글 수정 성공 테스트")
    @Test
    public void updateComment() throws Exception {
        BoardCommentRequestDto request = BoardCommentRequestDto.builder()
                .commentContent("Update test2.......")
                .boardId(1L)
                .id(8L)
                .build();


        mockMvc.perform(put("/comment/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

//        boolean check = commentService.updateBoardComment(request);
//        System.out.println(check);
    }


    @WithUserDetails("test")
    @DisplayName("[Controller] 댓글 삭제 성공 테스트")
    @Test
    @Disabled
    public void deleteComment() throws Exception{
//        given(commentService.deleteBoardCommentByCommentId(any(Long.class)))
//                .willReturn(any(Boolean.class));
//
//        mockMvc.perform(delete("/comment/")
//                        .content("{ \"commentId\" : 5}")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andDo(print());

    }
}