package com.web.puppylink.config.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {

    JOIN_MEMBER(2001, "봉사자로 회원가입이 되었습니다."),
    JOIN_FOUNDATION(2002, "재단으로 회원가입이 되었습니다."),
	EXIST_MEMBER(2010,"이미 가입되어 있는 유저가 존재합니다."),
    SUCCESS_LOGIN(2003, "로그인이 성공하였습니다."),
    FAILED_LOGIN(2004, "로그인이 실패하였습니다."),
	ALREADY_LOGOUT(2010,"로그아웃된 계정입니다."),
    SUCCESS_SECESSION(2005, "회원탈퇴가 정상적으로 되었습니다."),
    FAILED_SECESSION(2006, "회원탈퇴가 이루어지지 않았습니다."),
    SUCCESS_MAIL(2007, "메일 전송이 되었습니다."),
    INDEX_NOT_FOUND(2008, "인덱스가 존재하지 않습니다."),
    BOARD_NOT_FOUND(2009, "게시글을 찾을 수 없습니다."),
	
    SELECT_FOUNDATION(2101, "전체 단체 목록을 정상적으로 조회했습니다."),
    
    SELECT_FOUNDATION_VALOUNTEER(2102, "단체에 신청된 전체 봉사 신청 목록을 정상적으로 조회했습니다."),
	SELECT_FOUNDATION_VALOUNTEER_STATUS(2103, "단체 목록에 신청된 봉사 신청을 상태별로 분류하여 정상적으로 조회했습니다."),
	SELECT_MEMBER_VALOUNTEER(2104, "봉사자가 신청한 전체 봉사 신청 목록을 정상적으로 조회했습니다."),
	SELECT_MEMBER_VALOUNTEER_STATUS(2105, "봉사자가 신청한 봉사 신청을 상태별로 분류하여 정상적으로 조회했습니다."),
	SUBMIT_VALOUNTEER(2106, "봉사자의 봉사 신청이 성공적으로 신청되었습니다."),
	CANCEL_VALOUNTEER(2107, "봉사자의 봉사 신청이 성공적으로 취소되었습니다."),
	REGIST_VALOUNTEER(2108, "봉사자의 봉사 신청이 성공적으로 접수되었습니다."),
	REFUSE_VALOUNTEER(2109, "봉사자의 봉사 신청이 성공적으로 거절되었습니다."),
	OCR_VALOUNTEER(2110, "봉사자의 항공권이 정상적으로 OCR 처리되었습니다."),
	DOCS_VALOUNTEER(2111, "봉사자의 필수 서류가 정상적으로 제출되었습니다."),
	CONFIRM_VALOUNTEER(2112, "봉사자의 봉사 신청이 성공적으로 승인되었습니다."),
	LACK_VALOUNTEER(2113, "봉사자의 필수 서류가 정상적으로 미흡처리되었습니다."),
	COMPLETE_VALOUNTEER(2114, "봉사자의 봉사가 성공적으로 완료되었습니다."),
	
	SELECT_BOARD_ONE(2115, "게시글 조회가 완료되었습니다."),
	SELECT_BOARD_ALL(2116, "게시글 전체 조회가 완료되었습니다."),
	SELECT_BOARD_ALL_LIKE_MEMBER(2117, "좋아요 여부가 반영된 회원 게시글 전체 조회가 완료되었습니다."),
	SELECT_BOARD_ALL_LIKE_NON_MEMBER(2118, "좋아요 여부가 반영된 비회원 게시글 전체 조회가 완료되었습니다."),
	SELECT_BOARD_BEST(2119, "베스트 게시글 조회가 완료되었습니다."),
	SELECT_BOARD_BEST_NON(2120, "비회원 베스트 게시글 조회가 완료되었습니다."),
	SELECT_BOARD_BEST_MEMBER(2121, "회원 베스트 게시글 조회가 완료되었습니다."),
	SELECT_BOARD_RECENT(2122, "가장 최근에 작성된 게시글의 번호를 반환하였습니다."),
	SELECT_BOARD_INFINITE(2123, "무한 스크롤 게시글 조회가 완료되었습니다."),
	SELECT_BOARD_HISTORY(2124, "사용자가 작성한 게시글 목록 조회가 완료되었습니다."),
	SELECT_BOARD_LIKE(2125, "특정 게시글을 좋아요한 사용자 목록 조회가 완료되었습니다."),
	CREATE_BOARD(2126, "게시글 작성이 완료되었습니다."),
	UPDATE_BOARD(2127, "게시글 수정이 완료되었습니다."),
	DELETE_BOARD(2128, "게시글 삭제가 완료되었습니다."),
	LIKE_BOARD(2129, "게시글에 대한 좋아요가 완료되었습니다."),
	SELECT_COMMENT_ONE(2130, "게시글에 작성된 단일 댓글 조회가 완료되었습니다."),
	SELECT_COMMENT_ALL(2131, "게시글에 작성된 댓글 목록 조회가 완료되었습니다."),
	CREATE_COMMENT(2132, "댓글 작성이 완료되었습니다."),
	UPDATE_COMMENT(2133, "댓글 수정이 완료되었습니다."),
	DELETE_COMMENT(2134, "댓글 삭제가 완료되었습니다."),
	SELECT_PIC(2135, "게시글 사진 조회가 완료되었습니다."),

	SUCCESS_EMAIL(2201, "사용가능한 이메일입니다."),
    DUPLICATE_EMAIL(2202, "이미 사용중인 이메일입니다."),
    SUCCESS_NICKNAME(2203, "사용가능한 닉네임입니다."),
    DUPLICATE_NICKNAME(2204, "이미 사용중인 닉네임입니다."),
    SUCCESS_BUSINESSNO(2205, "사업자번호 인증에 성공하였습니다."),
	FAILED_BUSINESSNO(2206, "유효하지 않은 사업자번호입니다."),
	SUCCESS_S3_UPLOAD(2207, "s3 업로드에 성공하였습니다."),
	FAILED_S3_UPLOAD(2208, "s3 업로드에 실패했습니다."),
	SUCCESS_S3_DELETE(2209, "필수서류 삭제에 성공하였습니다."),
	FAILED_S3_DELETE(2210, "필수서류 삭제에 실패하였습니다"),
	SUCCESS_DESCRIPTION(2211, "단체 소개글 등록에 성공하였습니다."),
	SUCCESS_S3_UPDATE(2212, "필수서류 삭제 수정에 성공하였습니다."),
	FAILED_S3_UPDATE(2213, "필수서류 삭제 수정에 실패하였습니다"),
	SUCCESS_URL(2214, "필수서류 조회에 성공하였습니다."),
	FAILED_URL(2215, "필수서류 조회에 실패하였습니다"),
	
	SELECT_GPS(2301, "해당하는 편명의 실시간 항공기 정보를 성공적으로 조회했습니다."),
	FAILED_UPDATE_PWD(2302, "현재 비밀번호가 다릅니다."),
	SUCCESS_UPDATE_PWD(2303, "비밀번호가 성공적으로 변경됐습니다.");
	
	
    private int code;
    private String message;


}