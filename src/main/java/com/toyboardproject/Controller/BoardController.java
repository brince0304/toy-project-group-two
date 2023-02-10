package com.toyboardproject.controller;

import com.toyboardproject.Annotation.AuthCheck;
import com.toyboardproject.Annotation.BindingCheck;
import com.toyboardproject.Annotation.BoardAuthorCheck;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.domain.SearchType;
import com.toyboardproject.dto.BoardRequestDto;
import com.toyboardproject.dto.BoardResponseDto;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.service.BoardService;
import com.toyboardproject.utils.BoardUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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
    public ModelAndView getBoardInsertForm(@AuthenticationPrincipal PrincipalDto principal, @RequestParam BoardType type,@RequestParam(required = false) Long id) {
        if(type!=BoardType.FREE){
            if(!principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                return new ModelAndView("redirect:/board/?type="+type);
            }
            if(!principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                if(type.getValue().contains("FAQ")){
                    return new ModelAndView("redirect:/board/faq/"+type);
                }
            }
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/post");
        mav.addObject("boardType", type);
        if(id==null) {
            return mav;
        }
            mav.addObject("boardResponse", boardService.findBoardById(id));
            return mav;
    }

    //게시글 상세 페이지
    @AuthCheck
    @GetMapping("/{id}")
    public ModelAndView findBoardById(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("board/detail");
        mav.addObject("boardResponse",boardService.findBoardById(id));
        //mav.setViewName("상세뷰")
        return mav;
    }

    //게시글 리스트 페이지
    @AuthCheck
    @GetMapping("/")
    public ModelAndView boardList(@RequestParam BoardType type, @RequestParam(required = false) SearchType searchType
            , @RequestParam(required = false) String keyword
            ,@PageableDefault(sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        if(type.getValue().contains("FAQ")){
            return new ModelAndView("redirect:/board/faq/FAQ_PAYMENT");
        }
        Page<BoardResponseDto> response = boardService.searchBoardByKeywordAndSearchTypeAndBoardType(pageable,type,searchType,keyword);
        if(searchType==null){
            searchType=SearchType.TITLE;
        }
        if(keyword==null){
            keyword="";
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/index");
        mav.addObject("boardList",response);
        mav.addObject("boardType", BoardUtil.getBoardTypeName(type));
        mav.addObject("searchType",searchType);
        mav.addObject("keyword",keyword);
        return mav;
    }
    @AuthCheck
    @GetMapping("/faq/{type}")
    public ModelAndView faqList(@PathVariable BoardType type
            ,@PageableDefault(sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardResponseDto> response = boardService.searchBoardByKeywordAndSearchTypeAndBoardType(pageable,type,null,"");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/board/faq");
        mav.addObject("boardList",response);
        mav.addObject("boardType", BoardUtil.getBoardTypeName(type));
        return mav;
    }


    // 게시글 업데이트
    @AuthCheck
    @BoardAuthorCheck
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateBoardById(@AuthenticationPrincipal PrincipalDto principal, @PathVariable Long id, @Valid @RequestBody BoardRequestDto dto, BindingResult bindingResult){
        return new ResponseEntity<>(boardService.updateBoardById(id, dto), HttpStatus.OK);
    }

    // 게시글 삭제
    @AuthCheck
    @BoardAuthorCheck
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoardById(@AuthenticationPrincipal PrincipalDto principal, @PathVariable Long id){
        boardService.deleteBoardById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
