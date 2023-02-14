package com.web.puppylink.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.base.Charsets;
import com.web.puppylink.config.code.CommonCode;
import com.web.puppylink.config.code.ExceptionCode;
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.dto.BoardDto;
import com.web.puppylink.dto.FileDto;
import com.web.puppylink.model.File.FileRequest;
import com.web.puppylink.service.BoardServiceImpl;
import com.web.puppylink.service.FoundationServiceImpl;
import com.web.puppylink.service.VolunteerServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponseDto.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponseDto.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponseDto.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponseDto.class)
})
//@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/file")
public class FileController {
	
	private @Value("${cloud.aws.s3.bucket}") String S3Bucket;
	private final VolunteerServiceImpl volunteerService;
	private final FoundationServiceImpl foundationService;
	private final BoardServiceImpl boardService;
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	public FileController(
			VolunteerServiceImpl volunteerService,
			 FoundationServiceImpl foundationService,
			 BoardServiceImpl boardService) {
		this.volunteerService = volunteerService;
		this.foundationService = foundationService;
		this.boardService = boardService;
	}
	
	@Autowired
	AmazonS3Client amazonS3Client;
	
	// postman에서 테스트 
	@PostMapping(value= "/history")
	@ApiOperation(value = "파일 업로드 : 봉사자 필수서류 제출)")
	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiResponses(value = {
            @ApiResponse(code=200, message="성공적으로 업로드했습니다.", response = ResponseEntity.class)
        })
	public Object upload(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestPart FileDto fileDto) throws Exception {
		
		String imagePath = "";
		FileRequest fileRequest = null;
		
		// 폴더 이름 : nickName으로 생성
		String nickName = fileDto.getNickName();
		String ticketType = fileDto.getTicketType();
		int volunteerNo = fileDto.getVolunteerNo();
		
		try {
			// 파일 이름 : 랜덤숫자로 변경
			String fileName = multipartFile.getOriginalFilename(); 
			UUID uuid = UUID.randomUUID();
			// 파일 크기
			long size = multipartFile.getSize(); 
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(multipartFile.getContentType());
			objectMetaData.setContentLength(size);
			
			// 멤버 nickName으로 버킷 내 폴더 생성
			if(ticketType.equals("flight")) {
				fileName = "members/" + nickName + "/flight/" + uuid; 
			} else {
				fileName = "members/" + nickName + "/passport/" + uuid; 
			}

			// S3에 업로드
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			// 접근가능한 URL 가져오기
			imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); 
			
			fileRequest = FileRequest.builder()
					.nickName(nickName)
					.imagePath(imagePath)
					.volunteerNo(volunteerNo)
					.ticketType(ticketType)
					.build();
			
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
	                CommonCode.SUCCESS_S3_UPLOAD, volunteerService.submitFile(fileRequest)), HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.FAILED_S3_UPLOAD, volunteerService.submitFile(fileRequest)), HttpStatus.OK);
		}
	}
	
	@PutMapping(value= "/history")
    @ApiOperation(code = 200, value = "봉사자 필수서류 수정", notes = "필수서류를 수정한다.", response = ResponseEntity.class)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public Object update(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestPart FileDto fileDto) {
		
		String newImagePath = "";
		FileRequest fileRequest = null;
		
		// 폴더 이름 : nickName으로 생성
		String nickName = fileDto.getNickName();
		String ticketType = fileDto.getTicketType();
		int volunteerNo = fileDto.getVolunteerNo();
		
		try {
			// 파일 이름 : 랜덤숫자로 변경
			String fileName = multipartFile.getOriginalFilename(); 
			UUID uuid = UUID.randomUUID();
			// 파일 크기
			long size = multipartFile.getSize(); 
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(multipartFile.getContentType());
			objectMetaData.setContentLength(size);
			
			// 멤버 nickName으로 버킷 내 폴더 생성
			if(ticketType.equals("flight")) {
				fileName = "members/" + nickName + "/flight/" + uuid; 
			} else {
				fileName = "members/" + nickName + "/passport/" + uuid; 
			}

			// S3에 업로드
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			// 접근가능한 URL 가져오기
			newImagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString();
		
			fileRequest = FileRequest.builder()
					.nickName(nickName)
					.imagePath(newImagePath)
					.volunteerNo(volunteerNo)
					.ticketType(ticketType)
					.build();
			
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.SUCCESS_S3_UPDATE, volunteerService.updateFile(fileRequest)), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.FAILED_S3_UPDATE, volunteerService.updateFile(fileRequest)), HttpStatus.OK);
		}
    }
	
	@DeleteMapping("/history")
    @ApiOperation(value = "봉사자 필수 서류 삭제")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@ApiResponses(value = {
            @ApiResponse(code=200, message="성공적으로 필수서류를 삭제했습니다.", response = ResponseEntity.class)
        })
    public Object delete(@RequestBody FileDto fileDto) {
		
		FileRequest fileRequest = FileRequest.builder()
				.volunteerNo(fileDto.getVolunteerNo())
				.ticketType(fileDto.getTicketType())
				.build();
		try {
			volunteerService.deleteFile(fileRequest);
			
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.SUCCESS_S3_DELETE, null), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.FAILED_S3_DELETE, null), HttpStatus.OK);
		}
        
    }
	
	@PostMapping(value = "/profile", consumes = {"multipart/form-data" })
	@ApiOperation(value = "단체 프로필 등록")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER')")
	@ApiResponses(value = {
            @ApiResponse(code=200, message="성공적으로 단체프로필을 등록했습니다.", response = ResponseEntity.class)
        })
//	public Object uploadProfile(MultipartFile multipartFile, @RequestParam String nickName) throws Exception {
		public Object uploadProfile(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestPart String nickName) throws Exception {
		
		String imagePath = "";
		FileRequest fileRequest = null;
		
		try {
			String fileName = nickName; 
			
			// 한글 디코딩 
			fileName = URLDecoder.decode(fileName, "UTF-8");
			
			long size = multipartFile.getSize(); 
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(multipartFile.getContentType());
			objectMetaData.setContentLength(size);
			
			fileName = "foundation-profile" + "/" + fileName; 
			
			// S3에 업로드
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			// https://puppylink-test.s3.ap-northeast-2.amazonaws.com/foundation-profile/{businessName}
			imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); 
			
			fileRequest = FileRequest.builder()
					.nickName(nickName)
					.imagePath(imagePath)
					.build();
			
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
	                CommonCode.SUCCESS_S3_UPLOAD, foundationService.submitProfile(fileRequest)), HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
                    CommonCode.FAILED_S3_UPLOAD, foundationService.submitProfile(fileRequest)), HttpStatus.OK);
		}
	}
	
	//postman
	@PostMapping(value= "/board")
	@ApiOperation(value = "파일 업로드 - 게시판 사진")
	@ApiResponses(value = {
            @ApiResponse(code=200, message="성공적으로 업로드했습니다.", response = ResponseEntity.class)
        })
	public Object uploadPic(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam String boardNo) throws Exception {
		
		String imagePath = "";
		FileRequest fileRequest = null;
		
		try {
			// 파일 이름 : 랜덤숫자로 변경
			String fileName = multipartFile.getOriginalFilename(); 
			UUID uuid = UUID.randomUUID();
			// 파일 크기
			long size = multipartFile.getSize(); 
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(multipartFile.getContentType());
			objectMetaData.setContentLength(size);
			
			fileName = "board/" + uuid;

			// S3에 업로드
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			// 접근가능한 URL 가져오기
			imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); 
			
			fileRequest = FileRequest.builder()
					.imagePath(imagePath)
					.boardNo(Integer.parseInt(boardNo))
					.build();
			
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
	                CommonCode.SUCCESS_S3_UPLOAD, boardService.submitFile(fileRequest)), HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(new BasicResponseDto<CommonCode>(
	                CommonCode.FAILED_S3_UPLOAD, boardService.submitFile(fileRequest)), HttpStatus.OK);
		}
	}
	
}