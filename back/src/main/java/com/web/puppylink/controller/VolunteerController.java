package com.web.puppylink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.puppylink.dto.VolunteerDto;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.service.VolunteerServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponseDto.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponseDto.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponseDto.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponseDto.class) })

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
	
	private final VolunteerServiceImpl volunteerService;
	
	public VolunteerController(VolunteerServiceImpl volunteerService) {
		this.volunteerService = volunteerService;
	}
	
    @GetMapping("/select/foundation")
    @ApiOperation(value = "[단체] 단체에 신청된 봉사 신청 목록 조회 [STEP2-1]")
    public Object selectFoundation(@RequestParam(required = true) final String businessNo) {
        return ResponseEntity.ok(volunteerService.getFoundationVolunteer(businessNo));
    }
    
    @GetMapping("/select/foundation/status")
    @ApiOperation(value = "[단체] 단체에 신청된 봉사 신청 목록을 봉사 상태별로 분류하여 조회 [STEP2-2]")
    public Object selectFoundationStatus(@RequestParam(required = true) final String businessNo, 
    		@RequestParam(required = true) final String status) {
        return ResponseEntity.ok(volunteerService.getFoundationStatusVolunteer(businessNo, status));
    }
    
    @GetMapping("/select/member")
    @ApiOperation(value = "[봉사자] 봉사자가 신청한 봉사 목록 조회 [STEP2-3]")
    public Object selectMember(@RequestParam(required = true) final String email) {
    	return ResponseEntity.ok(volunteerService.getMemberVolunteer(email));
    }
	
	@GetMapping("/select/member/status")
    @ApiOperation(value = "[봉사자] 봉사자가 신청한 봉사 목록을 봉사 상태별로 분류하여 조회 [STEP2-4]")
    public Object selectMemberStatus(@RequestParam(required = true) final String email,
    		@RequestParam(required = true) final String status) {
    	return ResponseEntity.ok(volunteerService.getMembmerStatusVolunteer(email, status));
    }

    //	1. 신청 완료 : 봉사자가 봉사신청한 상태
    @PostMapping("/submit")
    @ApiOperation(value = "[봉사자] 신청 완료 [STEP1-1]")
    public Object submit(@RequestBody VolunteerDto volunteer) {
        return ResponseEntity.ok(volunteerService.submit(volunteer));
    }

    //	2. 신청 취소 : 봉사자가 신청한 봉사를 취소한 상태
    @DeleteMapping("/delete")
    @ApiOperation(value = "[봉사자] 신청 취소 [STEP1-2]")
    public void delete(@RequestParam(required = true) final int volunteerNo) {
       volunteerService.delete(volunteerNo);
    }
    
    //	3. 접수 완료 : 단체에서 봉사자를 선택한 상태
    @PutMapping("/regist")
    @ApiOperation(value = "[단체] 접수 완료 [STEP3-1]")
    public Object regist(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.regist(volunteerNo));
    }
    
    //	4. 접수 거절 : 단체가 봉사자의 신청을 거절한 상태
    @PutMapping("/cancel")
    @ApiOperation(value = "[단체] 봉사 신청 거절 [STEP3-2]")
    public Object cancel(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.cancel(volunteerNo));
    }
    
    //	5. 광학 문자 인식(OCR) : 봉사자가 항공권 정보 제출시, OCR을 통해 자동으로 정보를 입력하는 과정
    @GetMapping("/ocr")
    @ApiOperation(value = "[봉사자] 봉사자가 제출한 이미지에 대한 OCR 수행 [STEP4-1]")
    public Object ocr(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.ocr(volunteerNo));
    }
    
    //	6. 제출 완료 : 봉사자가 필수 서류를 제출한 상태
    @PutMapping("/docs")
    @ApiOperation(value = "[봉사자] 제출 완료 [STEP5-1]")
    public Object docs(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.docs(volunteerNo));
    }

    //	7. 승인 완료 : 이동봉사를 진행하고 있는 상태
    @PutMapping("/confirm")
    @ApiOperation(value = "[단체] 승인 완료 [STEP6-1]")
    public Object confirm(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.confirm(volunteerNo));
    }
    
    //	8. 서류 미흡 : 항공권의 데이터가 오기입 되었거나, 제출된 여권 사진이 적합하지 않은 상태
    @PutMapping("/lack")
    @ApiOperation(value = "[단체] 서류 미흡 [STEP6-2]")
    public Object lack(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.lack(volunteerNo));
    }
    
    //	9. 봉사 완료 : 봉사가 완료되어 입양 희망자에게 반려견이 인계된 상태
    @PutMapping("/complete")
    @ApiOperation(value = "[단체] 봉사 완료 [STEP7-1]")
    public Object complete(@RequestParam(required = true) final int volunteerNo) {
    	return ResponseEntity.ok(volunteerService.complete(volunteerNo));
    }
}
