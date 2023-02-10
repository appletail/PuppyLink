package com.web.puppylink.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.puppylink.config.auth.PrincipalDetails;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.GpsDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.dto.VolunteerDto;
import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.model.File.FileRequest;
import com.web.puppylink.ocr.GoogleVisionApi;
import com.web.puppylink.repository.FlightTicketRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("volunteerService")
public class VolunteerServiceImpl implements VolunteerService{
	private @Value("${cloud.aws.s3.bucket}") String Bucket;
	private @Value("${cloud.aws.region.static}") String Region;
	
	private final MemberRepository memberRepository;
	private final FoundationRepository foundationRepository;
	private final VolunteerRepository volunteerRepository;
	private final FlightTicketRepository flightTicketRepository;
	@Autowired
    private TokenProvider tokenProvider;
	
	public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
			MemberRepository memberRepository, FoundationRepository foundationRepository,
			FlightTicketRepository flightTicketRepository) {
		this.volunteerRepository = volunteerRepository;
		this.memberRepository = memberRepository;
		this.foundationRepository = foundationRepository;
		this.flightTicketRepository = flightTicketRepository;
	}
	
	@Transactional
	@Override
	public List<Volunteer> getMemberVolunteer(String nickName) {
		Member member = memberRepository.findByNickName(nickName).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		return volunteerRepository.findVolunteerByEmail(member);
	}
	
	@Transactional
	@Override
	public List<Volunteer> getMembmerStatusVolunteer(String nickName, String status) {
		Member member = memberRepository.findByNickName(nickName).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		return volunteerRepository.findVolunteerByEmailAndStatus(member, status);
	}
	
	@Transactional
	@Override
	public List<Volunteer> getFoundationVolunteer(String businessNo) {
		Foundation foundation = foundationRepository.findFoundationByBusinessNo(businessNo).orElseThrow(()->{
			return new IllegalArgumentException("단체 정보를 찾을 수 없습니다.");
		});
		return volunteerRepository.findVolunteerByBusinessNo(foundation);
	}
	
	@Transactional
	@Override
	public List<Volunteer> getFoundationStatusVolunteer(String businessNo, String status) {
		Foundation foundation = foundationRepository.findFoundationByBusinessNo(businessNo).orElseThrow(()->{
			return new IllegalArgumentException("단체 정보를 찾을 수 없습니다.");
		});
		return volunteerRepository.findVolunteerByBusinessNoAndStatus(foundation, status);
	}

	@Transactional
	@Override
	public Volunteer submit(VolunteerDto volunteer) {	
		Member member = memberRepository.findUserByEmail(volunteer.getEmail()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		Foundation foundation = foundationRepository.findFoundationByBusinessNo(volunteer.getBusinessNo()).orElseThrow(()->{
			return new IllegalArgumentException("단체 정보를 찾을 수 없습니다.");
		});
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String date = simpleDateFormat.format(new Date()); 
		
		Volunteer volunteerInfo = Volunteer.builder()
				.depTime(volunteer.getDepTime())
				.dest(volunteer.getDest())
				.passportURL(null)
				.flightURL(null)
				.flightName(volunteer.getFlightName())				
				.regDate(date)
				.status("submit")
				.businessNo(foundation)
				.email(member)
				.ticketNo(null)
				.build();
				
		return volunteerRepository.save(volunteerInfo);
	}

	public Volunteer submitFile(FileRequest file) {

		Member member = memberRepository.findByNickName(file.getNickName()).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(file.getVolunteerNo()).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		if(file.getTicketType().equals("flight")) {
			volunteer.setFlightURL(file.getImagePath());
		} else {
			volunteer.setPassportURL(file.getImagePath());
		}
		
		volunteerRepository.save(volunteer);
		
		return volunteer;
	}

	@Transactional
	@Override
	public void cancel(int volunteerNo) {
		// 봉사 상태(submit, refuse)에 따른 삭제 가능 조건 추가 예정
		volunteerRepository.deleteVolunteerByVolunteerNo(volunteerNo);
	}
	
	@Transactional
	@Override
	public Volunteer regist(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("submit")) {
			volunteer.setStatus("regist");			
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}
	
	@Transactional
	@Override
	public Volunteer refuse(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("submit")) {
			volunteer.setStatus("refuse");			
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}

	@Transactional
	@Override
	public Volunteer docs(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("regist") || status.contentEquals("lack") ) {
			volunteer.setStatus("docs");			
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}
	
	@Transactional
	@Override
	public Volunteer confirm(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("docs")) {
			volunteer.setStatus("confirm");
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}
	
	@Transactional
	@Override
	public Volunteer lack(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("docs")) {
			volunteer.setStatus("lack");
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}
	
	@Transactional
	@Override
	public Volunteer complete(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("승인 완료")) {
			volunteer.setStatus("봉사 완료");
			
			FileRequest fileRequest = FileRequest.builder()
					.volunteerNo(volunteerNo)
					.ticketType("all")
					.build();
			
			deleteFile(fileRequest);			// s3 필수서류 삭제	
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}

	@Transactional
	@Override
	public Volunteer ocr(int volunteerNo) {
		// 1. 봉사자가 업로드한 항공권 이미지 경로를 탐색한다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String path = volunteer.getFlightURL();
		// [AWS S3] path = "https://puppylink-test.s3.ap-northeast-2.amazonaws.com/ocr-test/flight.PNG";
		// [LOCAL] path = "src/image/001.PNG";
		
		// 2. 항공권 정보에 OCR을 적용하여 주요 정보를 데이터화한다.
		GoogleVisionApi api = new GoogleVisionApi(path);
		
		// 3. 항공권 정보를 객체에 담은 뒤, 데이터베이스에 저장한다.
		FlightTicket flightTicket = api.getFlightTicket();
		// flightTicketRepository.save(flightTicket);
		
		// 4. 봉사자의 항공권 정보(ticketNo)를 업데이트한다.
		volunteer.setTicketNo(flightTicket);
		
		return volunteer;
	}

	@Transactional
	@Override
	public void deleteFile(FileRequest file) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(file.getVolunteerNo()).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		try{
			String fileUrl = "";
			
			if(file.getTicketType().equals("flight")) {
				fileUrl = volunteer.getFlightURL();
			} else {
				fileUrl = volunteer.getPassportURL();
			}
			
			String fileKey= fileUrl.split("com/")[1]; 		

            final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Region).build();

            try {
                s3.deleteObject(Bucket, fileKey);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
	    } catch (Exception e) {
	       e.printStackTrace();
	       throw new RuntimeException("s3 객체를 삭제하는데 실패했습니다.");
	    }
		
		volunteer.setFlightURL(null);
	}
	
	@Transactional
	@Override
	public void deleteALLFile(TokenDto token) {
		Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String email = principal.getUsername();
        
		Member member = memberRepository.findUserByEmail(email).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		List<Volunteer> volList = volunteerRepository.findVolunteerByEmail(member);
		
		for (int i = 0; i < volList.size(); i++) {
			Volunteer volunteer = volList.get(i);
			int volunteerNo = volunteer.getVolunteerNo();
			
			// s3 항공권 삭제
			FileRequest fileRequest = FileRequest.builder()
					.volunteerNo(volunteerNo)
					.ticketType("flight")
					.build();
			
			deleteFile(fileRequest);						
			
			// s3 여권 삭제
			fileRequest = FileRequest.builder()
					.volunteerNo(volunteerNo)
					.ticketType("passport")
					.build();
			
			deleteFile(fileRequest);
			
			// db 삭제
			cancel(volunteerNo);							
		}
		
		
	}
	

	@Transactional
	@Override
	public ResponseEntity<String> flightInfo(int volunteerNo) {
		//1. 봉사자의 정보를 가져온다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		//2. 봉자자의 해외 이동 봉사에 이용되는 항공기 편명을 가져온다. 
		String flightNum = volunteer.getTicketNo().getFlight();
		System.out.println(flightNum);
		
		//3. AirLabs로 API 요청을 보내고 편명에 해당하는 각종 정보를 받는다.
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
			    " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
		
		//HttpBody 오브젝트 생성									
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("_view", "array");
		params.add("_fields", "lat,lng,dir");
		params.add("api_key", "fdf4a6cd-42f2-48d4-87f1-1a375d234b94");
		params.add("flight_iata", flightNum);

		HttpEntity<MultiValueMap<String, String>> airLabsRequest = 
				new HttpEntity<>(params, headers);
		
		ResponseEntity<String> response= rt.exchange(
				"https://airlabs.co/api/v9/flights",
				HttpMethod.POST,
				airLabsRequest,
				String.class
		);
		
		System.out.println("response.getBody() : " + response.getBody());

		return response;
	}

}
