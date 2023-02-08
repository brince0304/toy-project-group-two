package com.toyboardproject.service;

import com.toyboardproject.domain.BoardType;
import com.toyboardproject.domain.SearchType;
import com.toyboardproject.dto.PrincipalDto;
import com.toyboardproject.repository.BoardRepository;
import com.toyboardproject.domain.Board;
import com.toyboardproject.dto.BoardRequestDto;
import com.toyboardproject.dto.BoardResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 생성
    public Long createBoard(BoardRequestDto dto, PrincipalDto principal){
        return boardRepository.save(dto.toEntity(principal)).getId();
    }

    // 게시글 단건 조회
    public BoardResponseDto findBoardById(Long id){
        Board entity = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다"));
        return BoardResponseDto.from(entity);
    }

    // 게시글 검색 및 리스트 조회
    public Page<BoardResponseDto> searchBoardByKeywordAndSearchTypeAndBoardType(Pageable pageable, BoardType boardType, SearchType searchType, String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return boardRepository.findAll(pageable).map(BoardResponseDto::from);
        }
        return switch (searchType) {
            case TITLE -> boardRepository.findByBoardTitleContaining(boardType, keyword, pageable).map(BoardResponseDto::from);
            case CONTENT -> boardRepository.findByBoardContentContaining(boardType, keyword, pageable).map(BoardResponseDto::from);
            case USER_ID -> boardRepository.findByAccount_UserIdContaining(boardType, keyword, pageable).map(BoardResponseDto::from);
            case USER_NICKNAME -> boardRepository.findByAccount_UserNicknameContaining(boardType, keyword, pageable).map(BoardResponseDto::from);
        };
    }

        //게시글 수정
    public Long updateBoardById(Long id, BoardRequestDto dto, Long principal){
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("게시글 없음"));
            if(!entity.getBoardTitle().equals(dto.getTitle())){
                entity.setBoardTitle(dto.getTitle());
            }
            if(!entity.getBoardContent().equals(dto.getContent())){
                entity.setBoardContent(dto.getContent());
            }
        return id;
    }

        //게시글 삭제
    public void deleteBoardById(Long id, Long principal){
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("게시글 없음"));
        boardRepository.deleteById(id);
    }




}
