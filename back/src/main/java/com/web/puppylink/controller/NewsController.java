package com.web.puppylink.controller;

import java.util.List;

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
import com.web.puppylink.model.News;
import com.web.puppylink.service.BoardServiceImpl;
import com.web.puppylink.service.NewsServiceImpl;

import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/news")
public class NewsController {
	
	 private final NewsServiceImpl newsService;
	 
	 public NewsController(NewsServiceImpl newsService) {
		 this.newsService = newsService;
	 }

	 @GetMapping("/list")
	 public Object news() throws Exception{
	      List<News> newsList = newsService.getNewsData();
	      return newsList;
	 }
}
