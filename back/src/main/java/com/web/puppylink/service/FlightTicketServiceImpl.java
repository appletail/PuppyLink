package com.web.puppylink.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.ocr.GoogleVisionApi;
import com.web.puppylink.repository.FlightTicketRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("FlightTicketService")
public class FlightTicketServiceImpl implements FlightTicketService{
	private final FlightTicketRepository flightTicketRepository;
	private final VolunteerRepository volunteerRepository;
	
	public FlightTicketServiceImpl(FlightTicketRepository flightTicketRepository,
			VolunteerRepository volunteerRepository) {
		this.flightTicketRepository = flightTicketRepository;
		this.volunteerRepository = volunteerRepository;
	}

	@Override
	public FlightTicket submit(FlightTicket flightTicket) {
		return flightTicketRepository.save(flightTicket);
	}
	
	@Transactional
	@Override
	public FlightTicket ocr(int volunteerNo) {
		// 1. 봉사자가 업로드한 항공권 이미지 경로를 탐색한다.
		Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerNo(volunteerNo).orElseThrow(()->{
			return new IllegalArgumentException("봉사 정보를 찾을 수 없습니다.");
		});
		String path = volunteer.getFlightURL();
		// [AWS S3] path = "https://puppylink-test.s3.ap-northeast-2.amazonaws.com/ocr-test/flight.PNG";
		// [LOCAL] path = "src/image/001.PNG";
		
		// 2. 항공권 정보에 OCR을 적용하여 주요 정보를 데이터화한다.
		GoogleVisionApi api = new GoogleVisionApi(path);
		
		// 3. 항공권 정보를 객체에 담아 반환한다.
		FlightTicket flightTicket = api.getFlightTicket();
		
		return flightTicket;
	}
}