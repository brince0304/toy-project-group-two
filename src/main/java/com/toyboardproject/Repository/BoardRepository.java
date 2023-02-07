package com.toyboardproject.Repository;

import com.toyboardproject.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //List<Board> findAllByDelete(final boolean isDeleted, final Sort sort);

   // Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}