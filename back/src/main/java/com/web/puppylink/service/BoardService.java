package com.web.puppylink.service;

import java.util.List;

import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.dto.CommentDto;
import com.web.puppylink.model.Board;
import com.web.puppylink.model.Comment;

public interface BoardService {
	Board getBoard(int boardNo);
	List<Board> getBoardAll();
	Board create(BoardDto board);
	Board update(BoardDto board);
	void delete(int boardNo);
	void like(int boardNo, String nickName);
	Comment comment(CommentDto comment);
	Comment updateComment(CommentDto comment);
	void deleteComment(int commentNo);
	Comment getComment(int commentNo);
	List<Comment> getCommentAll(int boardNo);
}
