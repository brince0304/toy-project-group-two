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

@Validated
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //게시글 생성
    @PostMapping("/post")
    public ResponseEntity<Long> createBoard(@Valid @RequestBody BoardDto dto, BoardType boardType, BindingResult bindingResult){
        Long id = boardService.createBoard(dto, boardType);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    //게시글 생성 페이지 요청
    @GetMapping("/post")
    public String openBoardWrite() {
        return "board/post";
    }

    //게시글 상세 페이지
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> findBoardById(@PathVariable final Long id, Model model, BindingResult bindingResult) {
        BoardResponseDto responseDto = boardService.findBoardById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //게시글 리스트 페이지
    @GetMapping("/")
    public ResponseEntity<List> boardList(BoardType type) {
        List<BoardResponseDto> responseDtoList = boardService.findAll();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    //게시글 리스트 조회(삭제된거 빼고 출력되어야 함)
//    @GetMapping("/")
//    public List<BoardResponseDto> findAll(@RequestParam final boolean isDeleted){
//        return boardService.findAllWithoutDelete(true);
//    }


    // 게시글 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBoardById(@PathVariable Long id, @RequestBody BoardDto dto, BoardType boardType, BindingResult bindingResult){
        Long updateBoardId = boardService.updateBoardById(id, dto, boardType);
        return new ResponseEntity<>(updateBoardId, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoardById(@PathVariable Long id, BoardType boardType, BindingResult bindingResult){
        boolean result = boardService.deleteBoardById(id, boardType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //페이징
    @GetMapping("/")
    public String paging(Model model, BoardType boardType,
                         @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){

        Page<Board> list = boardService.paging(boardType, pageable);

        model.addAttribute("paging",list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        return "board/index";
    }

    // 검색 기능
//    @GetMapping("/board/search")
//    public String search(String keyword, Model model,
//                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
//                         Pageable pageable) {
//        Page<Board> searchList = boardService.search(keyword, pageable);
//
//        model.addAttribute("searchList", searchList);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
//        model.addAttribute("next", pageable.next().getPageNumber());
//        model.addAttribute("hasNext", searchList.hasNext());
//        model.addAttribute("hasPrev", searchList.hasPrevious());
//
//        return "posts-search";
//    }
}