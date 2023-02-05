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

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponseDto.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponseDto.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponseDto.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponseDto.class) })

@RestController
@RequestMapping("/foundation")
public class FoundationController {
	
	private final FoundationServiceImpl foundationService;
	
	public FoundationController(FoundationServiceImpl foundationService) {
		this.foundationService = foundationService;
	}
	
	// STEP1.
    @GetMapping("/all")
    @ApiOperation(value = "재단 전체 조회")
    public Object findAll() {
        return ResponseEntity.ok(foundationService.getFoundationAll());
    }
}
