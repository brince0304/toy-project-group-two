package com.toyboardproject.repository;

import com.toyboardproject.domain.Board;
import com.toyboardproject.domain.BoardComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    @EntityGraph(attributePaths = {"account"})
    List<BoardComment> findByBoard(Board board);

    List<BoardComment> findByBoardId(Long boardId);
}
