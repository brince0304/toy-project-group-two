package com.toyboardproject.controller;

import com.toyboardproject.Annotation.AuthCheck;
import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.dto.BoardRequestDto;
import com.toyboardproject.dto.BoardResponseDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //게시글 생성
    @PostMapping("/post")
    @BindingCheck
    public ResponseEntity<Long> createBoard(@AuthenticationPrincipal PrincipalDto principal, @Valid @RequestBody BoardRequestDto dto, BindingResult bindingResult){
        Long id = boardService.createBoard(dto,principal);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    //게시글 생성 페이지 요청
    @GetMapping("/post")
    @AuthCheck
    public ModelAndView getBoardInsertForm(@AuthenticationPrincipal PrincipalDto principal) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/post");
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
    //@GetMapping("/")
    //public ModelAndView boardList(@RequestParam BoardType type, SearchType searchType, String keyword) {
        //Page<BoardResponseDto> response = boardService.searchBoardByBoardTypeAndSearchTypeAndKeyWord(type,searchType,keyword);
        //ModelAndView mav = new ModelAndView();
        //mav.setViewName("/board/index")
        //mav.addAttribute("boardList",response);
        //mav.addAttribute("boardType",type);
        //mav.addAttribute("searchType",searchType);
        //mav.addAttribute("keyword",keyword);
        //return mav;
    //}




    // 게시글 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBoardById(@PathVariable Long id, @Valid @RequestBody BoardRequestDto dto, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.updateBoardById(id, dto), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoardById(@PathVariable Long id){
        boardService.deleteBoardById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }



}
