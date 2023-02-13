package com.web.puppylink.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.puppylink.config.code.CommonCode;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.dto.CommentDto;
import com.web.puppylink.service.BoardServiceImpl;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "[OK] 클라이언트의 요청에 대한 응답 성공", response = BasicResponseDto.class),
		@ApiResponse(code = 201, message = "[Created] 데이터 생성 혹은 수정 성공", response = BasicResponseDto.class),
		@ApiResponse(code = 204, message = "[No Content] 본문 없는 응답", response = BasicResponseDto.class),
		@ApiResponse(code = 401, message = "[Unauthorized] 유효한 자격 증명 부재", response = BasicResponseDto.class),
        @ApiResponse(code = 403, message = "[Forbidden] 서버의 클라이언트 접근 거부", response = BasicResponseDto.class),
        @ApiResponse(code = 404, message = "[Not Found] 요청한 리소스를 찾을 수 없음", response = BasicResponseDto.class),
        @ApiResponse(code = 500, message = "[Failure] 서버 내부 오류", response = BasicResponseDto.class) })

@RestController
@RequestMapping("/board")
public class BoardController {
	
private final BoardServiceImpl boardService;
	
	public BoardController(BoardServiceImpl boardService) {
		this.boardService = boardService;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/{boardNo}")
    @ApiOperation(code = 200, value = "게시글 단일 조회", notes = "특정 게시글 하나를 조회하여 반환한다.", response = ResponseEntity.class)
	@ApiImplicitParam(name = "boardNo", value = "게시글 번호(PK)", required = true, dataType = "int", example = "1")
    public Object selectBoard(@PathVariable final int boardNo) {
        // return ResponseEntity.ok(boardService.getBoard(boardNo));
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_BOARD_ONE,
            			boardService.getBoard(boardNo)
            	), 
            	HttpStatus.OK
            );
    }
	
    // 게시글을 최신 작성순대로 반환한다. (게시글 번호 내림차순)
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/list")
    @ApiOperation(code = 200, value = "게시글 전체 조회", notes = "전체 게시글 목록을 조회하여 반환한다.", response = ResponseEntity.class)
    public Object selectBoardAll() {
        // return ResponseEntity.ok(boardService.getBoardAll());
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_BOARD_ALL,
            			boardService.getBoardAll()
            	), 
            	HttpStatus.OK
            );
    }
    
    // 게시글을 좋아요가 높은 순서대로 반환한다. (게시글 좋아요 내림차순)
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/best")
    @ApiOperation(code = 200, value = "베스트 게시글 조회", notes = "베스트 게시글 목록을 조회하여 반환한다.", response = ResponseEntity.class)
    public Object selectBoardBest() {
        // return ResponseEntity.ok(boardService.getBestBoard());
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_BOARD_BEST,
            			boardService.getBoardBest()
            	), 
            	HttpStatus.OK
            );
    }
    
    // 가장 최근에 작성된 게시글의 번호를 반환한다.
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/recent")
    @ApiOperation(code = 200, value = "가장 최근에 작성된 게시글 번호 반환", notes = "가장 최근에 작성된 게시글의 번호를 반환한다.", response = ResponseEntity.class)
    public Object selectBoardRecent() {
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_BOARD_RECENT,
            			boardService.getBoardRecent()
            	), 
            	HttpStatus.OK
            );
    }
    
    // 무한 스크롤이 적용된 게시글을 반환한다.
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/infinite/{boardNo}")
    @ApiOperation(code = 200, value = "무한 스크롤 게시글 조회", notes = "현재 전달된 게시글보다 이전에 작성된 게시글 5개를 조회하여 반환한다.", response = ResponseEntity.class)
    public Object selectBoardInfinite(@PathVariable final int boardNo) {
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_BOARD_INFINITE,
            			boardService.getBoardInfinite(boardNo)
            	), 
            	HttpStatus.OK
            );
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping
    @ApiOperation(code = 200, value = "게시글 작성", notes = "게시글을 작성한다.", response = ResponseEntity.class)
    public Object create(@RequestBody BoardDto board) {
        // return ResponseEntity.ok(boardService.create(board));
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.CREATE_BOARD,
            			boardService.create(board)
            	), 
            	HttpStatus.OK
            );
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping
    @ApiOperation(code = 200, value = "게시글 수정", notes = "게시글을 수정한다.", response = ResponseEntity.class)
    public Object update(@RequestBody BoardDto board) {
        // return ResponseEntity.ok(boardService.update(board));
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.UPDATE_BOARD,
            			boardService.update(board)
            	), 
            	HttpStatus.OK
            );
    }
    
    @DeleteMapping
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiImplicitParam(name = "boardNo", value = "게시글 번호(PK)", required = true, dataType = "int", example = "1")
    public void delete(@RequestParam(required = true) final int boardNo) {
    	boardService.delete(boardNo);
    }
    
    @PutMapping("/like/{boardNo}/{nickName}")
    @ApiOperation(code = 200, value = "게시글 좋아요", notes = "게시글에 좋아요를 반영 혹은 취소한다.", response = ResponseEntity.class)
	@ApiImplicitParams({
    	@ApiImplicitParam(name = "boardNo", value = "게시글 번호(PK)", required = true, dataType = "int", example = "1"),    	
    	@ApiImplicitParam(name = "nickName", value = "회원 닉네임(Unique)", required = true, dataType = "string", defaultValue = "tom")
    })
    public void like(@PathVariable final int boardNo, @PathVariable final String nickName) {
    	boardService.like(boardNo, nickName);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PostMapping("/comment")
    @ApiOperation(code = 200, value = "댓글 작성", notes = "게시글에 댓글을 작성한다.", response = ResponseEntity.class)
    public Object comment(@RequestBody CommentDto comment) {
    	// return boardService.comment(comment);
        return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.CREATE_COMMENT,
            			boardService.comment(comment)
            	), 
            	HttpStatus.OK
            );
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PutMapping("/comment/{commentNo}}")
    @ApiOperation(code = 200, value = "댓글 수정", notes = "게시글에 작성된 댓글을 수정한다.", response = ResponseEntity.class)
    public Object updateComment(@RequestBody CommentDto comment) {
    	// return boardService.updateComment(comment);
    	return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.UPDATE_COMMENT,
            			boardService.updateComment(comment)
            	), 
            	HttpStatus.OK
            );
    }
    
    @DeleteMapping("/comment/{commentNo}")
    @ApiOperation(code = 200, value = "댓글 삭제", notes = "게시글에 작성된 댓글을 삭제한다.", response = ResponseEntity.class)
    public void deleteComment(@PathVariable final int commentNo) {
    	boardService.deleteComment(commentNo);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/comment/{commentNo}")
    @ApiOperation(code = 200, value = "게시글 단일 댓글 조회", notes = "게시글에 작성된 단일 댓글을 조회한다.", response = ResponseEntity.class)
    public Object selectComment(@PathVariable final int commentNo) {
    	// return boardService.getComment(commentNo);
    	return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_COMMENT_ONE,
            			boardService.getComment(commentNo)
            	), 
            	HttpStatus.OK
            );
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @GetMapping("/comment/list/{boardNo}")
    @ApiOperation(code = 200, value = "게시글 전체 댓글 조회", notes = "게시글에 작성된 전체 댓글을 조회한다.", response = ResponseEntity.class)
    public Object selectCommentAll(@PathVariable final int boardNo) {
    	// return boardService.getCommentAll(boardNo);
    	return new ResponseEntity<BasicResponseDto>(
            	new BasicResponseDto(
            			CommonCode.SELECT_COMMENT_ALL,
            			boardService.getCommentAll(boardNo)
            	), 
            	HttpStatus.OK
            );
    }
}
