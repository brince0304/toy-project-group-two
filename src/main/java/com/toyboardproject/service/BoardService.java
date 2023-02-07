package com.toyboardproject.service;

import com.toyboardproject.Repository.BoardRepository;
import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardType;
import com.toyboardproject.dto.BoardDto;
import com.toyboardproject.dto.BoardResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 생성
    // account dto객체 매개변수 추가 필요
    public Long createBoard(BoardDto dto, BoardType boardType){
        return boardRepository.save(dto.toEntity()).getId();
    }

    // 게시글 단건 조회
    public BoardResponseDto findBoardById(Long id){
        Board entity = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다"));
        return new BoardResponseDto(entity);
    }

    // 게시글 리스트 조회
    public List<BoardResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC,"id","createdAt");
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    //삭제된 게시글 빼고 리스트 조회
//    public List<BoardResponseDto> findAllWithoutDelete(boolean isDeleted) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
//        List<Board> list = boardRepository.findAllByDelete(true, sort);
//        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
//    }

    //게시글 수정
    // accountId 매개변수 추가 필요
    public Long updateBoardById(Long id, BoardDto dto, BoardType boardType){
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("게시글 없음"));
        entity.update(dto.getTitle(), dto.getContent());
        return id;
    }

    //게시글 삭제
    //accountId 매개변수 추가 필요
    public Boolean deleteBoardById(Long id, BoardType boardType){
        Board entity = boardRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("게시글 없음"));
        entity.delete();
        return true;
    }

    //페이징
    public Page<Board> paging(BoardType boardType, Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    // 검색 기능
//    public Page<Board> search(String keyword, Pageable pageable) {
//        Page<Board> postsList = boardRepository.findByTitleContaining(keyword, pageable);
//        return postsList;
//    }
}
