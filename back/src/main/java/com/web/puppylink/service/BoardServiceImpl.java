package com.web.puppylink.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.dto.CommentDto;
import com.web.puppylink.model.Board;
import com.web.puppylink.model.Comment;
import com.web.puppylink.model.Likes;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.model.File.FileRequest;
import com.web.puppylink.repository.BoardRepository;
import com.web.puppylink.repository.CommentRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.LikesRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("boardService")
public class BoardServiceImpl implements BoardService{
	private @Value("${cloud.aws.s3.bucket}") String Bucket;
	private @Value("${cloud.aws.region.static}") String Region;
	
	private final MemberRepository memberRepository;
	private final FoundationRepository foundationRepository;
	private final VolunteerRepository volunteerRepository;
	private final BoardRepository boardRepository;
	private final LikesRepository likesRepository;
	private final CommentRepository commentRepository;
	
	public BoardServiceImpl(VolunteerRepository volunteerRepository,
			MemberRepository memberRepository, FoundationRepository foundationRepository,
			BoardRepository boardRepository, LikesRepository likesRepository,
			CommentRepository commentRepository) {
		this.volunteerRepository = volunteerRepository;
		this.memberRepository = memberRepository;
		this.foundationRepository = foundationRepository;
		this.boardRepository = boardRepository;
		this.likesRepository = likesRepository;
		this.commentRepository = commentRepository;
	}
	
	@Transactional
	@Override
	public Board getBoard(int boardNo) {
		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		return boardInfo;
	}
	
	@Transactional
	@Override
	public List<Board> getBoardAll() {
		List<Board> boardInfoList = boardRepository.findBoardAllByOrderByBoardNoDesc().orElseThrow(()->{
			return new IllegalArgumentException("게시글 목록 정보를 찾을 수 없습니다.");
		});
		return boardInfoList;
	}
	
	@Transactional
	@Override
	public List<Board> getBoardBest() {
		List<Board> boardInfoList = boardRepository.findBoardAllByOrderByLikesDesc().orElseThrow(()->{
			return new IllegalArgumentException("베스트 게시글 목록 정보를 찾을 수 없습니다.");
		});
		return boardInfoList;
	}
	
	// 게시판 페이지에 존재하는 게시글의 번호 중, 가장 큰(가장 최근에 작성된) 번호를 반환받는다.
	@Transactional
	@Override
	public int getBoardRecent() {
		Board boardInfo = boardRepository.findTop1BoardByBoardNoLessThanOrderByBoardNoDesc(Integer.MAX_VALUE).orElseThrow(()->{
			return new IllegalArgumentException("작성된 게시글이 없습니다.");
		});
		return boardInfo.getBoardNo();
	}
	
	// 게시판 페이지에 존재하는 게시글의 번호 중, 가장 작은 번호를 매개변수로 전달받는다.
	@Transactional
	@Override
	public List<Board> getBoardInfinite(int boardNo) {
		List<Board> boardInfoList = boardRepository.findTop5BoardInfiniteByBoardNoLessThanOrderByBoardNoDesc(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("무한 스크롤 게시글 목록 정보를 찾을 수 없습니다.");
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
	@SuppressWarnings("unlikely-arg-type")
	@Transactional
	@Override
	public Board update(BoardDto board) {
		Board boardInfo = boardRepository.findBoardByBoardNo(board.getBoardNo()).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		Member memberInfo = memberRepository.findByEmail(board.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		// 기존 게시글의 작성자와 현재 수정된 게시글의 작성자가 일치하는 경우에만 수정을 진행한다.
		if(boardInfo.getEmail().getEmail() == memberInfo.getEmail()) {
			boardInfo.setContents(board.getContents());
			boardInfo.setPictureURL(board.getPictureURL());
			boardInfo.setSubject(board.getSubject());
			
			return boardInfo;
		}
		else {
			throw new IllegalArgumentException("게시글 접근 권한이 없습니다.");
		}
	}
	
	// 게시글 삭제시, 해당 게시글에 작성되어 있는 댓글들과 반영되어 있는 좋아요와 s3객체가 모두 삭제될 수 있도록 한다.
	@Transactional
	@Override
	public void delete(int boardNo) {
		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		
		List<Comment> commentList = commentRepository.findCommentByBoardNo(boardInfo);
		for(Comment comment : commentList) {
			commentRepository.delete(comment);
		}
		
		List<Likes> likesList = likesRepository.findLikesByBoardNo(boardInfo);
		System.out.println(likesList);
		for(Likes like : likesList) {
			likesRepository.delete(like);
		}
		deleteFile(boardNo);
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

	@Transactional
	@Override
	public Comment comment(CommentDto comment) {
		Board boardInfo = boardRepository.findBoardByBoardNo(comment.getBoardNo()).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		Member memberInfo = memberRepository.findByEmail(comment.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Comment commentInfo = Comment.builder()
				.letter(comment.getLetter())
				.regDate(now)
				.boardNo(boardInfo)
				.email(memberInfo)
				.build();
		
		return commentRepository.save(commentInfo);
	}
	
	@Transactional
	@Override
	public Comment updateComment(CommentDto comment) {
		Board boardInfo = boardRepository.findBoardByBoardNo(comment.getBoardNo()).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		Member memberInfo = memberRepository.findByEmail(comment.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});

		// 댓글이 작성된 게시글과 일치하는 경우에만 수정을 진행한다.
		if(boardInfo.getBoardNo() == comment.getBoardNo()) {
			// 기존 댓글의 작성자와 현재 수정된 댓글의 작성자가 일치하는 경우에만 수정을 진행한다.
			if(boardInfo.getEmail().getEmail() == memberInfo.getEmail()) {
				Comment commentInfo = commentRepository.findCommentByCommentNo(comment.getComentNo());
				commentInfo.setLetter(comment.getLetter());
				
				return commentInfo;
			}
			else {
				throw new IllegalArgumentException("댓글이 존재하지 않거나, 댓글 접근 권한이 없습니다.");
			}
		}
		else {
			throw new IllegalArgumentException("댓글이 존재하지 않거나, 댓글 접근 권한이 없습니다.");
		}
	}

	@Transactional
	@Override
	public void deleteComment(int commentNo) {
		commentRepository.deleteCommentByCommentNo(commentNo);
	}

	@Transactional
	@Override
	public Comment getComment(int commentNo) {
		return commentRepository.findCommentByCommentNo(commentNo);
	}
	
	@Transactional
	@Override
	public List<Comment> getCommentAll(int boardNo) {
		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		
		return commentRepository.findCommentByBoardNo(boardInfo);
	}

//	@Override
//	public List<Board> getBoardInfinite(int boardNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	// 게시글 사진 등록
	public Board submitFile(FileRequest file) {
		
		Board board = boardRepository.findBoardByBoardNo(file.getBoardNo()).orElseThrow(()->{
			return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
		});
		
		board.setPictureURL(file.getImagePath());
		
		boardRepository.save(board);
		
		return board;
	}
	
	@Transactional
	@Override
	public void deleteFile(int boardNo) {
		
		Board board = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
		});
		
		try{
			String fileUrl = board.getPictureURL();
			
			// fileURL없는 경우
			if(fileUrl != null && !fileUrl.equals("")) {
				String fileKey= fileUrl.split("com/")[1]; 		

	            final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Region).build();

	            try {
	                s3.deleteObject(Bucket, fileKey);
	            } catch (AmazonServiceException e) {
	                System.err.println(e.getErrorMessage());
	                System.exit(1);
	            }
			}
			
	    } catch (Exception e) {
	       e.printStackTrace();
	       throw new RuntimeException("s3 객체를 삭제하는데 실패했습니다.");
	    }
		
		board.setPictureURL(null);
	}

	public Object getPic(int boardNo) {

		Board boardInfo = boardRepository.findBoardByBoardNo(boardNo).orElseThrow(()->{
			return new IllegalArgumentException("게시글 정보를 찾을 수 없습니다.");
		});
		if(boardInfo.getPictureURL() == null) {
			return "해당 게시글엔 사진이 없습니다";
		} else {
			return boardInfo.getPictureURL();
		}
	}
}
