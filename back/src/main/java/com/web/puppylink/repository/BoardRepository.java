package com.web.puppylink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Board;
import com.web.puppylink.model.Member;

public interface BoardRepository extends JpaRepository<Board,String>{
	Optional<Board> findBoardByBoardNo(int boardNo);
	Optional<List<Board>> findBoardAllByOrderByBoardNoDesc();
	Optional<List<Board>> findBoardAllByOrderByLikesDesc();
	Optional<List<Board>> findBoardAllByOrderByLikesDescBoardNoDesc();
	Optional<Board> findTop1BoardByBoardNoLessThanOrderByBoardNoDesc(int boardNo);
	Optional<List<Board>> findTop5BoardInfiniteByBoardNoLessThanOrderByBoardNoDesc(int boardNo);
	Optional<List<Board>> findBoardAllByEmail(Member email);
	void deleteBoardByBoardNo(int boardNo);
}
