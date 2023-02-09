package com.web.puppylink.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.model.Board;
import com.web.puppylink.model.Likes;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.BoardRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.LikesRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("boardService")
public class BoardServiceImpl implements BoardService{
	
	private final MemberRepository memberRepository;
	private final FoundationRepository foundationRepository;
	private final VolunteerRepository volunteerRepository;
	private final BoardRepository boardRepository;
	private final LikesRepository likesRepository;
	
	public BoardServiceImpl(VolunteerRepository volunteerRepository,
			MemberRepository memberRepository, FoundationRepository foundationRepository,
			BoardRepository boardRepository, LikesRepository likesRepository) {
		this.volunteerRepository = volunteerRepository;
		this.memberRepository = memberRepository;
		this.foundationRepository = foundationRepository;
		this.boardRepository = boardRepository;
		this.likesRepository = likesRepository;
	}
	
	@Override
	public Board getBoard(int boardNo) {
		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		return boardInfo;
	}
	
	@Override
	public List<Board> getBoardAll() {
		List<Board> boardInfoList = boardRepository.findBoardAllByOrderByBoardNo().orElseThrow(()->{
			return new IllegalArgumentException("게시글 목록 정보를 찾을 수 없습니다.");
		});
		return boardInfoList;
	}
	
	// boardNo는 데이터베이스에서 자동 삽입되므로(Auto Increment) 삽입 정보에서 생략한다.
	@Transactional
	@Override
	public Board create(BoardDto board) {
		Member member = memberRepository.findUserByEmail(board.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String date = simpleDateFormat.format(new Date()); 
		
		Board boardInfo = Board.builder()
				.contents(board.getContents())
				.likes(board.getLikes())
				.pictureURL(board.getPictureURL())
				.regDate(date)
				.subject(board.getSubject())
				.email(member)
				.build();
				
		return boardRepository.save(boardInfo);
	}
	
	// 게시글 수정은 제목, 내용, 첨부파일 경로만 수정할 수 있도록 한다.
	@Transactional
	@Override
	public Board update(BoardDto board) {
		Board boardInfo = boardRepository.findBoardByBoardNo(board.getBoardNo()).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		boardInfo.setContents(board.getContents());
		boardInfo.setPictureURL(board.getPictureURL());
		boardInfo.setSubject(board.getSubject());
				
		return boardInfo;
	}
	
	@Transactional
	@Override
	public void delete(int boardNo) {
		boardRepository.deleteBoardByBoardNo(boardNo);
	}

	@Transactional
	@Override
	public void like(int boardNo, String nickName) {
		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		Member memberInfo = memberRepository.findByNickName(nickName).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		Likes likes = likesRepository.findLikesByBoardNoAndEmail(boardInfo, memberInfo);
		
		// 해당 게시물에 대한 좋아요 이력이 없는 경우, 해당 게시물에 대해 좋아요를 진행한다.
		if(likes==null) {		
			Likes likesInfo = Likes.builder()
					.boardNo(boardInfo)
					.email(memberInfo)
					.build();
			
			boardInfo.setLikes(Integer.toString(Integer.parseInt(boardInfo.getLikes())+1));
			likesRepository.save(likesInfo);
		}
		
		// 해당 게시물에 대한 좋아요 이력이 있는 경우, 해당 게시물에 대한 좋아요를 취소한다.
		else if(likes!=null) {		
			boardInfo.setLikes(Integer.toString(Integer.parseInt(boardInfo.getLikes())-1));
			likesRepository.delete(likes);
		}
	}
}
