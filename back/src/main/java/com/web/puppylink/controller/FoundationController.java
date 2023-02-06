package com.web.puppylink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.service.FoundationServiceImpl;

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
@RequestMapping("/foundation")
public class FoundationController {
	
	private final FoundationServiceImpl foundationService;
	
	public FoundationController(FoundationServiceImpl foundationService) {
		this.foundationService = foundationService;
	}
	
	// STEP2.
    @GetMapping("/all")
    @ApiOperation(value = "[단체] 재단 전체 조회 [STEP2-1]", notes = "봉사 신청 페이지에서 봉사자가 단체에 대한 설명을 보고 고를 수 있도록, 전체 단체에 대한 정보를 반환한다. ")
    public Object findAll() {
        return ResponseEntity.ok(foundationService.getFoundationAll());
    }
}
