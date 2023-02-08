package com.toyboardproject.repository;

import com.toyboardproject.domain.Board;

import com.toyboardproject.domain.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByBoardTitleContaining(BoardType boardType, String keyword, Pageable pageable);
    Page<Board> findByBoardContentContaining(BoardType boardType, String keyword, Pageable pageable);
    Page<Board> findByAccount_UserIdContaining(BoardType boardType, String keyword, Pageable pageable);
    Page<Board> findByAccount_UserNicknameContaining(BoardType boardType, String keyword, Pageable pageable);



}