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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.web.puppylink.dto.BasicResponseDto;
import com.web.puppylink.dto.FileDto;
import com.web.puppylink.model.File.FileRequest;
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
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	public FileController(
			VolunteerServiceImpl volunteerService,
			 FoundationServiceImpl foundationService) {
		this.volunteerService = volunteerService;
		this.foundationService = foundationService;
	}
	
	@Autowired
	AmazonS3Client amazonS3Client;
	
	// postman에서 테스트 
	@PostMapping(value= "/history")
	@ApiOperation(value = "봉사자 필수서류 제출")
	public Object upload(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestPart FileDto fileDto) throws Exception {
		
		List<String> imagePathList = new ArrayList<>();
		String imagePath = "";
		FileRequest fileRequest = null;
		
		// 폴더 이름 : nickName으로 생성
		String nickName = fileDto.getNickName();
		String ticketType = fileDto.getTicketType();
		int volunteerNo = fileDto.getVolunteerNo();
		
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
			imagePathList.add(imagePath);
			
			fileRequest = FileRequest.builder()
					.nickName(nickName)
					.imagePath(imagePath)
					.volunteerNo(volunteerNo)
					.ticketType(ticketType)
					.build();
		
		return ResponseEntity.ok(volunteerService.submitFile(fileRequest));
	}
	
	@DeleteMapping("/history")
    @ApiOperation(value = "봉사자 필수 서류 삭제")
    public void delete(@RequestBody FileDto fileDto) {
		
		FileRequest fileRequest = FileRequest.builder()
				.volunteerNo(fileDto.getVolunteerNo())
				.ticketType(fileDto.getTicketType())
				.build();
		
        volunteerService.deleteFile(fileRequest);
    }
	
	@PostMapping(value = "/profile", consumes = {"multipart/form-data" })
	@ApiOperation(value = "단체 프로필 등록")
	public Object uploadProfile(MultipartFile multipartFile, @RequestParam String nickName) throws Exception {
		
		String imagePath = "";
		FileRequest fileRequest = null;
		
			String fileName = nickName; 
			
			// 한글 디코딩 
			fileName = URLDecoder.decode(fileName, Charsets.UTF_8); 			
			
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
		
		return ResponseEntity.ok(foundationService.submitProfile(fileRequest));
	}
}