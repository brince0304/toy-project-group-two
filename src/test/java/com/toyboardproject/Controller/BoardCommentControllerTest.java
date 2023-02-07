package com.toyboardproject.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyboardproject.Repository.AccountRepository;
import com.toyboardproject.Repository.BoardRepository;
import com.toyboardproject.Service.BoardCommentService;
import com.toyboardproject.config.SecurityConfig;
import com.toyboardproject.domain.Account;
import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.dto.BoardCommentRequestDto;


import com.toyboardproject.dto.BoardCommentResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @Autowired private BoardCommentService commentService;

    @Autowired private BoardRepository boardRepository;

    @Autowired private AccountRepository accountRepository;


    @Test
    public void insertAccount() {
        Account account1 = Account.builder()
                .userId("test1")
                .userName("홍길동")
                .userNickname("Spring")
                .userPassword("12345678")
                .userPhoneNum("01012345678")
                .build();

        accountRepository.save(account1);
    }

    @Test
    public void insertBoard() {
        Account account = Account.builder().id(1L).build();

        Board board = Board.builder()
                .account(account)
                .boardTitle("test1")
                .boardContent("test....")
                .boardType(BoardType.FREE)
                .build();

        boardRepository.save(board);
    }

    @DisplayName("[Controller] 댓글 저장 성공 테스트")
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
        given(commentService.getBoardCommentsByBoardId(any(Long.class)))
                .willReturn(any());

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

    @WithUserDetails(value = "test1", userDetailsServiceBeanName = "customUserDetailsService")
    @DisplayName("[Controller] 댓글 수정 성공 테스트")
    @Test
    public void updateComment() throws Exception {
        BoardCommentRequestDto request = BoardCommentRequestDto.builder()
                .commentContent("Update test2.......")
                .boardId(1L)
                .id(8L)
                .build();

        given(commentService.updateBoardComment(any(BoardCommentRequestDto.class)))
                .willReturn(any(Boolean.class));

        mockMvc.perform(put("/comment/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

//        boolean check = commentService.updateBoardComment(request);
//        System.out.println(check);
    }


    @WithUserDetails(value = "test1", userDetailsServiceBeanName = "customUserDetailsService")
    @DisplayName("[Controller] 댓글 삭제 성공 테스트")
    @Test
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

        boolean check = commentService.deleteBoardCommentByCommentId(5L);
        System.out.println(check);
    }
}