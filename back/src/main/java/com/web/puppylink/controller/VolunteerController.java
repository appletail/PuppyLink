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
@RequestMapping("/volunteer")
public class VolunteerController {
	
	private final VolunteerServiceImpl volunteerService;
	
	public VolunteerController(VolunteerServiceImpl volunteerService) {
		this.volunteerService = volunteerService;
	}
	
    @GetMapping("/select/foundation")
    @ApiOperation(value = "[단체] 단체에 신청된 봉사 신청 목록 조회 [STEP2-2]", notes = "단체가 봉사 페이지에 접근했을 때, 해당 단체에 신청된 봉사 목록 전체를 반환한다.")
    @ApiImplicitParam(name = "businessNo", value = "단체 번호(PK)", required = true, dataType = "string", defaultValue = "1148209801")
    public Object selectFoundation(@RequestParam(required = true) final String businessNo) {
        return ResponseEntity.ok(volunteerService.getFoundationVolunteer(businessNo));
    }
    
    @GetMapping("/select/foundation/status")
    @ApiOperation(value = "[단체] 단체에 신청된 봉사 신청 목록을 봉사 상태별로 분류하여 조회 [STEP2-3]", notes = "단체가 봉사 페이지에 접근했을 때, 해당 단체에 신청된 봉사 목록을 봉사 상태별로 분류하여 반환한다.  "
    		+ "\n" +"봉사 상태는 다음과 같다. (신청 완료 → 접수 완료/접수 거절 → 제출 완료 → 승인 완료/서류 미흡 → 봉사 완료)")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "businessNo", value = "단체 번호(PK)", required = true, dataType = "string", defaultValue = "1148209801"),
    	@ApiImplicitParam(name = "status", value = "봉사 상태", required = true, dataType = "string", defaultValue = "신청 완료")    	
    })
    public Object selectFoundationStatus(@RequestParam(required = true) final String businessNo, 
    		@RequestParam(required = true) final String status) {
        return ResponseEntity.ok(volunteerService.getFoundationStatusVolunteer(businessNo, status));
    }
    
    @GetMapping("/select/member")
    @ApiOperation(value = "[봉사자] 봉사자가 신청한 봉사 목록 조회 [STEP2-4]", notes = "봉사자가 봉사 페이지에 접근했을 때, 해당 봉사자가 신청한 봉사 목록 전체를 반환한다.")
    @ApiImplicitParam(name = "email", value = "봉사자 이메일(PK)", required = true, dataType = "string", defaultValue = "admin")
    public Object selectMember(@RequestParam(required = true) final String email) {
    	return ResponseEntity.ok(volunteerService.getMemberVolunteer(email));
    }
	
	@GetMapping("/select/member/status")
    @ApiOperation(value = "[봉사자] 봉사자가 신청한 봉사 목록을 봉사 상태별로 분류하여 조회 [STEP2-4]", notes = "봉사자가 봉사 페이지에 접근했을 때, 해당 봉사자가 신청한 봉사 목록을 봉사 상태별로 분류하여 반환한다.  "
    		+ "\n" +"봉사 상태는 다음과 같다. (신청 완료 → 접수 완료/접수 거절 → 제출 완료 → 승인 완료/서류 미흡 → 봉사 완료)")
	@ApiImplicitParams({
    	@ApiImplicitParam(name = "email", value = "봉사자 이메일(PK)", required = true, dataType = "string", defaultValue = "admin"),
    	@ApiImplicitParam(name = "status", value = "봉사 상태", required = true, dataType = "string", defaultValue = "신청 완료")    	
    })
	public Object selectMemberStatus(@RequestParam(required = true) final String email,
    		@RequestParam(required = true) final String status) {
    	return ResponseEntity.ok(volunteerService.getMembmerStatusVolunteer(email, status));
    }

    //	1. 신청 완료 : 봉사자가 봉사신청한 상태
    @PostMapping("/submit")
    @ApiOperation(value = "[봉사자] 신청 완료 [STEP1-1]", notes = "[신청 완료] 봉사자는 간단한 탑승 정보를 입력한 뒤, 봉사를 신청한다. 이때, 대괄호에 포함된 단어는 신청된 봉사의 상태(Status)를 의미한다.  "
    		+ "\n" + "봉사 신청 프로세스는 STEP 순서대로 따라가면 된다. STEP2에 해당하는 단체 정보 반환은 FoundationController에서 수행할 수 있다.  "
    		+ "\n" + "※ STEP 순서대로 메소드를 정렬하고 싶었지만.. Swagger에서 메소드 정렬 기능을 제공하지 않는다는 점 양해 부탁드립니다.")
    public Object submit(@RequestBody VolunteerDto volunteer) {
        return ResponseEntity.ok(volunteerService.submit(volunteer));
    }

    //	2. 신청 취소 : 봉사자가 신청한 봉사를 취소한 상태
    @DeleteMapping("/delete")
    @ApiOperation(value = "[봉사자] 신청 취소 [STEP1-2]", notes = "[신청 완료 → 상태 없음] 봉사자가 신청한 봉사를 취소한다. 봉사 정보는 데이터베이스에서 완전히 제거된다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public void delete(@RequestParam(required = true) final int volunteerNo) {
       volunteerService.delete(volunteerNo);
    }
    
    //	3. 접수 완료 : 단체에서 봉사자를 선택한 상태
    @PutMapping("/regist")
    @ApiOperation(value = "[단체] 접수 완료 [STEP3-1]", notes = "[신청 완료 → 접수 완료] 봉사 번호를 통해, 단체에서 봉사자가 신청한 봉사를 접수한다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object regist(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.regist(volunteerNo));
    }
    
    //	4. 접수 거절 : 단체가 봉사자의 신청을 거절한 상태
    @PutMapping("/cancel")
    @ApiOperation(value = "[단체] 봉사 신청 거절 [STEP3-2]", notes = "[신청 완료 → 접수 거절] 봉사 번호를 통해, 단체에서 봉사자가 신청한 봉사를 거절한다. 봉사자는 해당 봉사에 대해 삭제만 가능하다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object cancel(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.cancel(volunteerNo));
    }
    
    //	5. 광학 문자 인식(OCR) : 봉사자가 항공권 정보 제출시, OCR을 통해 자동으로 정보를 입력하는 과정
    @GetMapping("/ocr")
    @ApiOperation(value = "[봉사자] 봉사자가 제출한 이미지에 대한 OCR 수행 [STEP4-1]", notes = "[접수 완료] AWS S3에 업로드된 항공권 이미지에 OCR을 적용한 뒤, 탑승권 주요 정보를 추출하여 반환한다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object ocr(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.ocr(volunteerNo));
    }
    
    //	6. 제출 완료 : 봉사자가 필수 서류를 제출한 상태
    @PutMapping("/docs")
    @ApiOperation(value = "[봉사자] 제출 완료 [STEP5-1]", notes = "[접수 완료/서류 미흡 → 제출 완료] 봉사자가 여권 사진에 대한 업로드 및 항공권 이미지에 대한 OCR 처리 후, 필수 서류를 제출한다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object docs(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.docs(volunteerNo));
    }

    //	7. 승인 완료 : 이동봉사를 진행하고 있는 상태
    @PutMapping("/confirm")
    @ApiOperation(value = "[단체] 승인 완료 [STEP6-1]", notes = "[제출 완료 → 승인 완료] 단체는 봉사자가 제출한 필수 서류가 적합하다고 판단한 경우, 봉사를 승인한다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object confirm(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.confirm(volunteerNo));
    }
    
    //	8. 서류 미흡 : 항공권의 데이터가 오기입 되었거나, 제출된 여권 사진이 적합하지 않은 상태
    @PutMapping("/lack")
    @ApiOperation(value = "[단체] 서류 미흡 [STEP6-2]", notes = "[제출 완료 → 서류 미흡] 단체는 봉사자가 제출한 필수 서류가 부적합하다고 판단한 경우, 서류 재제출을 요구한다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object lack(@RequestParam(required = true) final int volunteerNo) {
        return ResponseEntity.ok(volunteerService.lack(volunteerNo));
    }
    
    //	9. 봉사 완료 : 봉사가 완료되어 입양 희망자에게 반려견이 인계된 상태
    @PutMapping("/complete")
    @ApiOperation(value = "[단체] 봉사 완료 [STEP7-1]", notes = "[승인 완료 → 봉사 완료] 입양 희망자에게 반려견이 인계된 경우, 봉사가 완료된다.")
    @ApiImplicitParam(name = "volunteerNo", value = "봉사 번호(PK)", required = true, dataType = "int", defaultValue = "1")
    public Object complete(@RequestParam(required = true) final int volunteerNo) {
    	return ResponseEntity.ok(volunteerService.complete(volunteerNo));
    }
}
