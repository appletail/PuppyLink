package com.web.puppylink.service;

import java.util.List;

import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.model.Board;

public interface BoardService {
	Board getBoard(int boardNo);
	List<Board> getBoardAll();
	Board create(BoardDto board);
	Board update(BoardDto board);
	void delete(int boardNo);
	void like(int boardNo, String nickName);
}
