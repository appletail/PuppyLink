package com.web.puppylink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Board;
import com.web.puppylink.model.Comment;
import com.web.puppylink.model.Member;

public interface CommentRepository extends JpaRepository<Comment,String>{
	List<Comment> findCommentByBoardNo(Board board);
	void deleteCommentByCommentNo(int commentNo);
	Comment findCommentByCommentNo(int commentNo);
}
