package com.toyboardproject.controller;

import com.toyboardproject.Annotation.AuthCheck;
import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.domain.SearchType;
import com.toyboardproject.dto.BoardRequestDto;
import com.toyboardproject.dto.BoardResponseDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;


@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;


    //게시글 생성
    @AuthCheck
    @PostMapping("/post")
    @BindingCheck
    public ResponseEntity<Long> createBoard(@AuthenticationPrincipal PrincipalDto principal, @Valid @RequestBody BoardRequestDto dto, BindingResult bindingResult){
        Long id = boardService.createBoard(dto,principal);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // 게시글 생성 및 수정 페이지 요청
    @GetMapping("/post")
    @AuthCheck
    public ModelAndView getBoardInsertForm(@RequestParam(required = false) BoardType type,@RequestParam(required = false) Long id,@AuthenticationPrincipal PrincipalDto principal) {
        ModelAndView mav = new ModelAndView();
        BoardResponseDto responseDtoId = boardService.findBoardById(id);
        mav.addObject("boardType", type);
        mav.setViewName("board/post");

        if(id > 0){
            mav.addObject("responseDtoId", responseDtoId);
        }
        return mav;
    }

    //게시글 상세 페이지
    @GetMapping("/{id}")
    public ModelAndView findBoardById(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.findBoardById(id);
        ModelAndView mav = new ModelAndView("board/detail");
        mav.addObject("boardResponse",responseDto);
        //mav.setViewName("상세뷰")
        return mav;
    }

    //게시글 리스트 페이지
    @GetMapping("/")
    public ModelAndView boardList(@RequestParam(required = false) BoardType type, @RequestParam(required = false) SearchType searchType, @RequestParam(required = false) String keyword, Pageable pageable) {
        Page<BoardResponseDto> response = boardService.searchBoardByKeywordAndSearchTypeAndBoardType(pageable,type,searchType,keyword);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/index");
        mav.addObject("boardList",response);
        mav.addObject("boardType",type);
        mav.addObject("searchType",searchType);
        mav.addObject("keyword",keyword);
        return mav;
    }


    // 게시글 업데이트
    @AuthCheck
    @PutMapping("/")
    public ResponseEntity<Long> updateBoardById(@RequestParam Long id, @Valid @RequestBody BoardRequestDto dto, @AuthenticationPrincipal PrincipalDto principal, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.updateBoardById(id, dto, principal.getId()), HttpStatus.OK);
    }

    // 게시글 삭제
    @AuthCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoardById(@PathVariable Long id, @AuthenticationPrincipal PrincipalDto principal){
        boardService.deleteBoardById(id, principal.getId());
        return new ResponseEntity<>( HttpStatus.OK);
    }



}
