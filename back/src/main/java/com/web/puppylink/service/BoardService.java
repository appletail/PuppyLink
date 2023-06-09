package com.web.puppylink.service;

import java.util.List;

import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.dto.BoardLikesDto;
import com.web.puppylink.dto.BoardTokenDto;
import com.web.puppylink.dto.CommentDto;
import com.web.puppylink.model.Board;
import com.web.puppylink.model.Comment;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.File.FileRequest;

public interface BoardService {
	Board getBoard(int boardNo);
	List<Board> getBoardAll();
	List<Board> getBoardBest();
	int getBoardRecent();
	List<Board> getBoardInfinite(int boardNo);
	Board create(BoardDto board);
	Board update(BoardDto board);
	void delete(int boardNo);
	void like(int boardNo, String nickName);
	Comment comment(CommentDto comment);
	Comment updateComment(CommentDto comment);
	void deleteComment(int commentNo);
	Comment getComment(int commentNo);
	List<Comment> getCommentAll(int boardNo);
	Board submitFile(FileRequest file);
	void deleteFile(int boardNo);
	Object getPic(int boardNo); 
	List<Board> getBoardHistory(String nickName);
	List<Member> getBoardLike(int boardNo);
	List<BoardLikesDto> getBoardAllLikeNonMember();
	List<BoardLikesDto> getBoardAllLikeMember(BoardTokenDto token) throws Exception;
	
//	List<BoardLikesDto> getBoardInfiniteNonMember(int boardNo);
//	List<BoardLikesDto> getBoardInfiniteMember(BoardTokenDto token, int boardNo) throws Exception;
	
	List<BoardLikesDto> getBoardBestNonMember();
	List<BoardLikesDto> getBoardBestMember(BoardTokenDto token) throws Exception;
}
