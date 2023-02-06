package com.web.puppylink.service;

import java.util.List;
import java.util.Optional;

import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.dto.VolunteerDto;
import com.web.puppylink.model.Volunteer;

public interface VolunteerService {
	// ※ 정규 봉사 신청 프로세스
	//	1. [봉사자] 신청 완료 : 봉사자가 봉사신청한 상태
	//	2. [봉사자] 신청 취소 : 봉사자가 신청한 봉사를 취소한 상태
	//	3. [단체] 접수 완료 : 단체에서 봉사자를 선택한 상태
	//	4. [단체] 접수 거절 : 단체가 봉사자의 신청을 거절한 상태
	//	5. [봉사자] 제출 완료 : 봉사자가 필수 서류를 제출한 상태
	//	6. [단체] 승인 완료 : 이동봉사를 진행하고 있는 상태
	//	7. [단체] 서류 미흡 : 항공권의 데이터가 오기입 되었거나, 제출된 여권 사진이 적합하지 않은 상태
	//	8. [단체] 봉사 완료 : 봉사가 완료되어 입양 희망자에게 반려견이 인계된 상태
	
	// ※ <봉사자> 신청한 봉사 조회 : 봉사자가 신청한 본인의 봉사 신청 목록을 조회한다.
	List<Volunteer> getMemberVolunteer(String email);
	
	// ※ <재단> 신청된 봉사 조회 : 재단에 신청된 봉사 목록을 조회한다.
	List<Volunteer> getFoundationVolunteer(String businessNo);
	
	// 1. <봉사자> [신청 완료] : 봉사자가 간편 정보를 입력하여 1차 봉사 신청을 완료한 경우
	Volunteer submit(VolunteerDto volunteer);
	
	// 2. <봉사자> [신청 취소] : 봉사자가 신청한 봉사를 취소한 상태 -> 봉사 튜플이 완전히 삭제된다.
	void delete(int volunteerNo);
	
	// 3. <재단> [접수 완료] : 재단이 봉사자의 신청을 1차 승인한 경우 -> 봉사자의 '서류 제출' 버튼 활성화
	Volunteer regist(int volunteerNo);
	
	// 4. <재단> [접수 거절] : 재단이 봉사자의 신청을 거절한 경우
	Volunteer cancel(int volunteerNo); // 봉사 승인 취소
	
	// 5. <봉사자> [제출 완료] : 봉사자가 필수 서류를 제출한 상태
	Volunteer docs(int volunteerNo);
	
	// 6. <재단> [승인 완료] : 재단이 봉사자의 서류 제출을 확인한 경우
	Volunteer confirm(int volunteerNo);
	
	// 7. <재단> [서류 미흡] : 재단이 봉사자의 서류가 미흡하다고 판단한 경우
	Volunteer lack(int volunteerNo);
	
	// 8. <재단> [봉사 완료] : 해외 이동 봉사가 모두 종료된 경우, 재단에서 확정
	Volunteer complete(int volunteerNo); 
	
	// ※ 단체의 봉사 관리 페이지에서, 드롭다운 버튼에 따른 봉사 상태(Status)에 따른 목록 조회
	List<Volunteer> getFoundationStatusVolunteer(String businessNo, String status);
	
	// ※ 봉사자의 마이 페이지에서, 드롭다운 버튼에 따른 봉사 상태(Status)에 따른 목록 조회
	List<Volunteer> getMembmerStatusVolunteer(String email, String status);		
	
	// ※ Google Vision Api를 통해, 업로드된 항공권 이미지에 대한 OCR 결과를 반환 및 저장
	Volunteer ocr(int volunteerNo);

	// 봉사자 필수서류 삭제
	void deleteFile(int volunteerNo);

	// 회원탈퇴 시 s3에 저장된 필수서류 삭제
	void deleteALLFile(TokenDto token);
}
