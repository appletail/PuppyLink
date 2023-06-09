package com.web.puppylink.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

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
import com.web.puppylink.config.auth.PrincipalDetails;
import com.web.puppylink.config.jwt.TokenProvider;
import com.web.puppylink.dto.AirportDto;
import com.web.puppylink.dto.FlightTicketDto;
import com.web.puppylink.dto.GpsDto;
import com.web.puppylink.dto.TokenDto;
import com.web.puppylink.dto.VolunteerDto;
import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Location;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.model.File.FileRequest;
import com.web.puppylink.repository.FlightTicketRepository;
import com.web.puppylink.repository.FoundationRepository;
import com.web.puppylink.repository.LocationRepository;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("volunteerService")
public class VolunteerServiceImpl implements VolunteerService{
	private @Value("${cloud.aws.s3.bucket}") String Bucket;
	private @Value("${cloud.aws.region.static}") String Region;
	private @Value("${api.serviceKeyGps}") String apiKeyGps;
	
	private final MemberRepository memberRepository;
	private final FoundationRepository foundationRepository;
	private final VolunteerRepository volunteerRepository;
	private final FlightTicketRepository flightTicketRepository;
	private final LocationRepository locationRepository;
	@Autowired
    private TokenProvider tokenProvider;
	
	public VolunteerServiceImpl(VolunteerRepository volunteerRepository,
			MemberRepository memberRepository, FoundationRepository foundationRepository,
			FlightTicketRepository flightTicketRepository, LocationRepository locationRepository) {
		this.volunteerRepository = volunteerRepository;
		this.memberRepository = memberRepository;
		this.foundationRepository = foundationRepository;
		this.flightTicketRepository = flightTicketRepository;
		this.locationRepository = locationRepository;
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
	public Volunteer docs(FlightTicketDto flightTicket) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(flightTicket.getVolunteerNo()).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("regist") || status.contentEquals("lack") ) {
			volunteer.setStatus("docs");	
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		
		// OCR 처리 결과를 반환할 항공권 객체를 생성한다.
		FlightTicket flightTicketInfo = FlightTicket.builder()
				.ticketNo(flightTicket.getTicketNo())
				.passengerName(flightTicket.getPassengerName())
				.bookingReference(flightTicket.getBookingReference())
				.depCity(flightTicket.getDepCity())
				.depDate(flightTicket.getDepDate())
				.arriveCity(flightTicket.getArriveCity())
				.arriveDate(flightTicket.getArriveDate())
				.flight(flightTicket.getFlight().replaceAll(" ", ""))
				.build();
		
		// 봉사자의 항공권 정보(ticketNo)를 업데이트한다.
		volunteer.setTicketNo(flightTicketRepository.save(flightTicketInfo));
		
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
		if(status.contentEquals("confirm")) {
			volunteer.setStatus("complete");
			
			//flightticket 테이블에서 삭제
			String ticketNo = volunteer.getTicketNo().getTicketNo();	
			flightTicketRepository.deleteById(ticketNo);
			//location 테이블에서 삭제
			locationRepository.deleteTicket(ticketNo);
			
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
			
			// fileURL없는 경우
			if(fileUrl != null && !fileUrl.equals("")) {
				String fileKey= fileUrl.split("com/")[1]; 		

	            final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Region).build();

	            try {
	                s3.deleteObject(Bucket, fileKey);
	            } catch (AmazonServiceException e) {
	                System.err.println(e.getErrorMessage());
	                System.exit(1);
	            }
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
		System.out.println(volList.size());
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
	public AirportDto airportInfo(int volunteerNo) {
		//1. 봉사자의 정보를 가져온다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		//2. 봉자자의 티켓정보에서 출발공항 id, 도착공항 id를 가져온다. 
		String depCity = volunteer.getTicketNo().getDepCity();
		String arriveCity = volunteer.getTicketNo().getArriveCity();
		
		
		//3. AirLabs로 API 요청을 보내고 출발, 도착 공항에 해당하는 각종 정보를 받는다.
		RestTemplate rt = new RestTemplate();
		

		//3-1. 도착 공항 데이터 요청
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
			    " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
		
		//HttpBody 오브젝트 생성									
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("iata_code", depCity);
		params.add("api_key", apiKeyGps);

		HttpEntity<MultiValueMap<String, String>> airLabsRequest = 
				new HttpEntity<>(params, headers);
		
		ResponseEntity<Map> response = rt.exchange(
				"https://airlabs.co/api/v9/airports",
				HttpMethod.POST,
				airLabsRequest,
				Map.class
		);
		ArrayList ls =  (ArrayList) response.getBody().get("response");
		LinkedHashMap map = (LinkedHashMap) ls.get(0);

		Double depLat = (Double) map.get("lat");  
		Double depLng = (Double) map.get("lng");
		System.out.println("depLat : "+ depLat);
		
		//3-2. 도착 공항 데이터 요청
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
			    " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
		
		//HttpBody 오브젝트 생성									
		MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
		params2.add("iata_code", arriveCity);
		params2.add("api_key", apiKeyGps);

		HttpEntity<MultiValueMap<String, String>> airLabsRequest2 = 
				new HttpEntity<>(params2, headers2);
		
		ResponseEntity<Map> response2 = rt.exchange(
				"https://airlabs.co/api/v9/airports",
				HttpMethod.POST,
				airLabsRequest2,
				Map.class
		);
		System.out.println("response2 : " + response2);
		System.out.println("response2.body : " + response2.getBody());
		System.out.println("response2.header : " + response2.getHeaders());
		System.out.println("response2.getStatusCodeValue : " + response2.getStatusCodeValue());
		System.out.println("response2.getBody().get(\"response\") : " + response2.getBody().get("response"));

		ArrayList m =  (ArrayList) response2.getBody().get("response");
//		System.out.println("response2.getBody().get(\"response\") : " + response2.getBody().get("response"));
		System.out.println("ArrayList m  : "  + m);
		System.out.println("m.get(0) : " +  m.get(0));
		LinkedHashMap a = (LinkedHashMap) m.get(0);
		System.out.println("LinkedHashMap a : " + a);
		System.out.println("a.keyset" + a.keySet());
		System.out.println("a.get(\"lat\") :"  + a.get("lat"));
		
		Double arriveLat = (Double) a.get("lat");  
		Double arriveLng = (Double) a.get("lng");
		System.out.println( "arriveLat : " + arriveLat);
		 

		
    	AirportDto airportDto = AirportDto.builder()
		.depCity(depCity)
		.depLat(depLat)
		.depLng(depLng)
		.arriveCity(arriveCity)
		.arriveLat(arriveLat)
		.arriveLng(arriveLng)
		.build();
		
//		AirportDto airportDto = new AirportDto(depCity, depLat, depLng);
    	
    	System.out.println("airportDto : " + airportDto.toString());
		return airportDto;
		
	}

	@Transactional
	@Override
	public ResponseEntity<String> flightInfo(int volunteerNo) {
		//1. 봉사자의 정보를 가져온다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		//2. 봉자자의 해외 이동 봉사에 이용되는 항공기 편명을 가져온다. 
		String flightNum = volunteer.getTicketNo().getFlight().replaceAll(" ", "");
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
		params.add("api_key", apiKeyGps);
		params.add("flight_iata", flightNum);

		HttpEntity<MultiValueMap<String, String>> airLabsRequest = 
				new HttpEntity<>(params, headers);
		
		ResponseEntity<String> response= rt.exchange(
				"https://airlabs.co/api/v9/flights",
				HttpMethod.POST,
				airLabsRequest,
				String.class
		);
		return response;
	}


	@Transactional
	@Override
	public void flightInfoDb(String ticketNo, @NotNull String flight) {
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
		params.add("api_key", apiKeyGps);
		params.add("flight_iata", flight);
		
		HttpEntity<MultiValueMap<String, String>> airLabsRequest = 
				new HttpEntity<>(params, headers);
		
		ResponseEntity<List> response= rt.exchange(
				"https://airlabs.co/api/v9/flights",
				HttpMethod.POST,
				airLabsRequest,
				List.class
		);
		
		List s = response.getBody();
		List arr = (List) s.get(0);

		//우선 해당 오브젝트를 String으로 변환한 후 Integer.parseInt
		Double tmpLat = Double.parseDouble(String.valueOf(arr.get(0)));
		Double tmpLng = Double.parseDouble(String.valueOf(arr.get(1)));
		Double tmpDir = Double.parseDouble(String.valueOf(arr.get(2)));
//		System.out.println(tmpLat + "  " + tmpLng + " " + tmpDir );
		locationRepository.mSave(ticketNo, flight, tmpLat, tmpLng, tmpDir);
//		System.out.println("msave 완료");
		return;
	}

	@Transactional
	@Override
	public Volunteer updateFile(FileRequest file) {

		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(file.getVolunteerNo()).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		try {
			deleteFile(file);
			
			String ticketType = file.getTicketType();
			String imagePath = file.getImagePath();
			
			if(ticketType.equals("flight")) {
				volunteer.setFlightURL(imagePath);
			} else {
				volunteer.setPassportURL(imagePath);
			}
			
		} catch (Exception e) {
			  e.printStackTrace();
			  throw new RuntimeException("s3 객체를 수정하는데 실패했습니다.");
		}
		return volunteer;
	}

	public String getPassportUrl(int volunteerN) {

		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerN).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		return volunteer.getPassportURL();
	}

	public Object getFlightInto(int volunteerN) {

		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerN).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		FlightTicket ticketInfo = volunteer.getTicketNo();
		String name = volunteer.getEmail().getName();
		String status = volunteer.getStatus();
		
		ArrayList<Object> list = new ArrayList<>();
		list.add(name);
		list.add(status);
		list.add(ticketInfo);
		
		return list;
	}

	@Transactional
	@Override
	public List<Location> pathInfo(int volunteerNo) {
		//1. 봉사자의 정보를 가져온다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		//2. 봉자자의 해외 이동 봉사에 이용되는 티켓 넘버를 가져온다. 
		String flightNum = volunteer.getTicketNo().getFlight().replaceAll(" ", "");
//		System.out.println(flightNum);
		
		List<Location> pathList = locationRepository.findAllByFlight(flightNum);
		return pathList;
	}
}
