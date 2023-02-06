package com.web.puppylink.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.web.puppylink.dto.VolunteerDto;
import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
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
	public List<Volunteer> getMemberVolunteer(String email) {
		Member member = memberRepository.findUserByEmail(email).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		return volunteerRepository.findVolunteerByEmail(member);
	}
	
	@Transactional
	@Override
	public List<Volunteer> getMembmerStatusVolunteer(String email, String status) {
		Member member = memberRepository.findUserByEmail(email).orElseThrow(()->{
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
		
		Volunteer volunteerInfo = Volunteer.builder()
				.depTime(volunteer.getDepTime())
				.dest(volunteer.getDest())
				.fileURL(null)
				.flightName(volunteer.getFlightName())				
				.regDate(new Date().toString())
				.status("신청 완료")
				.businessNo(foundation)
				.email(member)
				.ticketNo(null)
				.build();
				
		return volunteerRepository.save(volunteerInfo);
	}

	public Volunteer submitFile(String nickName, String imagePath, int volunteerNo) {

		Member member = memberRepository.findByNickName(nickName).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		volunteer.setFileURL(imagePath);
		volunteerRepository.save(volunteer);
		
		return volunteer;
	}

	@Transactional
	@Override
	public void delete(int volunteerNo) {
		volunteerRepository.deleteVolunteerByVolunteerNo(volunteerNo);
	}
	
	@Transactional
	@Override
	public Volunteer regist(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("신청 완료")) {
			volunteer.setStatus("접수 완료");			
		}
		else {
			throw new IllegalArgumentException("올바른 프로세스가 아닙니다.");
		}
		return volunteer;
	}
	
	@Transactional
	@Override
	public Volunteer cancel(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String status = volunteer.getStatus();
		if(status.contentEquals("신청 완료")) {
			volunteer.setStatus("접수 거절");			
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
		if(status.contentEquals("접수 완료")) {
			volunteer.setStatus("제출 완료");			
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
		if(status.contentEquals("제출 완료")) {
			volunteer.setStatus("승인 완료");
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
		if(status.contentEquals("제출 완료")) {
			volunteer.setStatus("서류 미흡");
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
			deleteFile(volunteerNo);			// s3 필수서류 삭제	
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
		String path = volunteer.getFileURL();
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
	public void deleteFile(int volunteerNo) {
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		
		try{
			String fileUrl = volunteer.getFileURL();   		
            String fileKey = fileUrl.split("com/")[1]; 		

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
//	       throw new BaseException(S3_DELETE_ERROR);
	    }
		
		volunteer.setFileURL(null);
	}
	
	@Transactional
	@Override
	public void deleteALLFile(String email) {
		Member member = memberRepository.findUserByEmail(email).orElseThrow(()->{
			return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
		});
		List<Volunteer> volList = volunteerRepository.findVolunteerByEmail(member);
		
		for (int i = 0; i < volList.size(); i++) {
			Volunteer volunteer = volList.get(i);
			int volunteerNo = volunteer.getVolunteerNo();
			deleteFile(volunteerNo);						// s3 삭제
			delete(volunteerNo);							// db 삭제
		}
		
		
	}
}
