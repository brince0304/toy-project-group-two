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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 생성
    public Long createBoard(BoardRequestDto dto, PrincipalDto principal){
        if(dto.getBoardType()==null){
            throw new IllegalArgumentException("게시판 타입을 선택해주세요.");
            //spring security check role
        }if(dto.getBoardType()==BoardType.NOTICE &&  !principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new IllegalArgumentException("공지사항은 관리자만 작성할 수 있습니다.");
        }if(dto.getBoardType().getValue().contains("FAQ") && !principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new IllegalArgumentException("FAQ는 관리자만 작성할 수 있습니다.");}
        return boardRepository.save(dto.toEntity(principal)).getId();
    }

    // 게시글 단건 조회
    public BoardResponseDto findBoardById(Long id){
        Board entity = boardRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("해당 게시글이 없습니다"));
        return BoardResponseDto.from(entity);
    }

    // 게시글 검색 및 리스트 조회
    public Page<BoardResponseDto> searchBoardByKeywordAndSearchTypeAndBoardType(Pageable pageable, BoardType boardType, SearchType searchType, String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return boardRepository.findAllByBoardType(boardType,pageable).map(BoardResponseDto::from);
        }
        return switch (searchType) {
            case TITLE -> boardRepository.findBoardsByBoardTitleContainingIgnoreCaseAndBoardType(keyword,boardType , pageable).map(BoardResponseDto::from);
            case CONTENT -> boardRepository.findBoardsByBoardContentContainingIgnoreCaseAndBoardType(keyword,boardType,  pageable).map(BoardResponseDto::from);
            case USER_ID -> boardRepository.findBoardsByAccount_UserIdAndBoardType( keyword,boardType, pageable).map(BoardResponseDto::from);
            case USER_NICKNAME -> boardRepository.findBoardsByAccount_UserNicknameAndBoardType(keyword,boardType,  pageable).map(BoardResponseDto::from);
        };
    }

        //게시글 수정
    public Long updateBoardById(Long id, BoardRequestDto dto){
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
            if(!entity.getBoardTitle().equals(dto.getTitle())){
                entity.setBoardTitle(dto.getTitle());
            }
            if(!entity.getBoardContent().equals(dto.getContent())){
                entity.setBoardContent(dto.getContent());
            }
        return id;
    }

        //게시글 삭제
    public void deleteBoardById(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new EntityNotFoundException("게시글이 존재하지 않습니다.");
        }
        boardRepository.deleteById(id);
    }




}
