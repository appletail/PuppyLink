package com.web.puppylink.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.web.puppylink.service.MemberServiceImpl;
import com.web.puppylink.service.VolunteerServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class FileController {
	
	private @Value("${cloud.aws.s3.bucket}") String S3Bucket;
	private final VolunteerServiceImpl volunteerService;
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	public FileController(VolunteerServiceImpl volunteerService) {
		this.volunteerService = volunteerService;
	}
	
	@Autowired
	AmazonS3Client amazonS3Client;
	
	@PostMapping("/upload/{nickName}/history/submit")
	@ApiOperation(value = "필수서류 제출")
	public Object upload(MultipartFile[] multipartFileList, HttpServletRequest request) throws Exception {
		List<String> imagePathList = new ArrayList<>();
		String imagePath = "";
		// 폴더 이름 : nickName으로 생성
		String nickName = getFolderName(request.getRequestURI(), "/");
		
		for(MultipartFile multipartFile: multipartFileList) {
			// 파일 이름 : 랜덤숫자로 변경
			String fileName = multipartFile.getOriginalFilename(); 
			UUID uuid = UUID.randomUUID();
			long size = multipartFile.getSize(); // 파일 크기
			
			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(multipartFile.getContentType());
			objectMetaData.setContentLength(size);
			
			// 멤버 nickName으로 버킷 내 폴더 생성
			fileName = nickName + "/" + uuid; 
			System.out.println(fileName);
			
			// S3에 업로드
			amazonS3Client.putObject(
				new PutObjectRequest(S3Bucket, fileName, multipartFile.getInputStream(), objectMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead)
			);
			
			imagePath = amazonS3Client.getUrl(S3Bucket, fileName).toString(); // 접근가능한 URL 가져오기
			imagePathList.add(imagePath);
			
		}
		
		return ResponseEntity.ok(volunteerService.submitFile(nickName, imagePath));
	}
	
	private String getFolderName(String uri, String regex) {
        String[] split = uri.split(regex);
        
        return split.length < 2 ? "" : split[2];
    }
}