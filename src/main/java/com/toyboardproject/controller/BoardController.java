package com.toyboardproject.controller;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.domain.SearchType;
import com.toyboardproject.dto.BoardDto;
import com.toyboardproject.dto.BoardResponseDto;
import com.toyboardproject.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //게시글 생성
    @PostMapping("/post")
    public ResponseEntity<Long> createBoard(@Valid @RequestBody BoardDto dto, BindingResult bindingResult){
        Long id = boardService.createBoard(dto, boardType);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    //게시글 생성 페이지 요청
    @GetMapping("/post")
    public ModelAndView getBoardInsertForm() {
        ModelAndView mav = new ModelAndView();
        //mav.setViewName("board/post");
        return mav;
    }

    //게시글 상세 페이지
    @GetMapping("/{id}")
    public ModelAndView findBoardById(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.findBoardById(id);
        ModelAndView mav = new ModelAndView();
        mav.addAttribute("boardResponse",responseDto);
        //mav.setViewName("상세뷰")
        return mav;
    }

    //게시글 리스트 페이지
    @GetMapping("/")
    public ModelAndView boardList(@RequestParam BoardType type, SearchType searchType, String keyword) {
        Page<BoardResponseDto> response = boardService.searchBoardByBoardTypeAndSearchTypeAndKeyWord(type,searchType,keyword);
        ModelAndView mav = new ModelAndView();
        //mav.setViewName("/boards/")
        //mav.addAttribute("boardList",response);
        //mav.addAttribute("boardType",type);
        //mav.addAttribute("searchType",searchType);
        //mav.addAttribute("keyword",keyword);
        return mav;
    }




    // 게시글 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBoardById(@PathVariable Long id,@Valid @RequestBody BoardDto dto, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.updateBoardById(id, dto, boardType), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoardById(@PathVariable Long id){
        return new ResponseEntity<>(boardService.deleteBoardById(id, boardType), HttpStatus.OK);
    }



}
