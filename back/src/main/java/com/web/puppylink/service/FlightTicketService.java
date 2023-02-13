package com.web.puppylink.service;

import com.web.puppylink.model.FlightTicket;

public interface FlightTicketService {
	FlightTicket submit(FlightTicket flightTicket);
	
	// ※ Google Vision Api를 통해, 업로드된 항공권 이미지에 대한 OCR 결과를 반환 및 저장
	FlightTicket ocr(int volunteerNo);
}
