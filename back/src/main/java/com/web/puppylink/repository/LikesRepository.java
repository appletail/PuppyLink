package com.web.puppylink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Board;
import com.web.puppylink.model.Likes;
import com.web.puppylink.model.Member;

public interface LikesRepository extends JpaRepository<Likes,String>{
	Likes findLikesByBoardNoAndEmail(Board board, Member member);
	List<Likes> findLikesByBoardNo(Board board);
	List<Likes> findLikesByEmail(Member member);
}
