package com.toyboardproject.repository;

import com.toyboardproject.domain.Board;

import com.toyboardproject.domain.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findBoardsByBoardTitleContainingIgnoreCaseAndBoardType( String boardTitle,BoardType boardType, Pageable pageable);
    Page<Board> findBoardsByBoardContentContainingIgnoreCaseAndBoardType(String boardContent,BoardType boardType,  Pageable pageable);
    Page<Board> findBoardsByAccount_UserIdAndBoardType( String userId,BoardType boardType, Pageable pageable);
    Page<Board> findBoardsByAccount_UserNicknameAndBoardType(String keyword,BoardType boardType , Pageable pageable);

    Page<Board> findAllByBoardType(BoardType boardType , Pageable pageable);


}