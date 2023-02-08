package com.web.puppylink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Board;

public interface BoardRepository extends JpaRepository<Board,String>{
	Board findBoardByBoardNo(int boardNo);
	List<Board> findBoardAllByOrderByBoardNo();
	void deleteBoardByBoardNo(int boardNo);
}
