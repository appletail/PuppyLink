package com.web.puppylink.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.model.Board;
import com.web.puppylink.model.Member;
import com.web.puppylink.repository.BoardRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("boardService")
public class BoardServiceImpl implements BoardService{
	
	private final MemberRepository memberRepository;
	private final FoundationRepository foundationRepository;
	private final VolunteerRepository volunteerRepository;
	private final BoardRepository boardRepository;
	
	public BoardServiceImpl(VolunteerRepository volunteerRepository,
			MemberRepository memberRepository, FoundationRepository foundationRepository,
			BoardRepository boardRepository) {
		this.volunteerRepository = volunteerRepository;
		this.memberRepository = memberRepository;
		this.foundationRepository = foundationRepository;
		this.boardRepository = boardRepository;
	}
	
	@Override
	public Board getBoard(int boardNo) {
		return boardRepository.findBoardByBoardNo(boardNo);
	}
	
	@Override
	public List<Board> getBoardAll() {
		return boardRepository.findBoardAllByOrderByBoardNo();
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
		Board boardInfo = boardRepository.findBoardByBoardNo(board.getBoardNo());
	
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



}
