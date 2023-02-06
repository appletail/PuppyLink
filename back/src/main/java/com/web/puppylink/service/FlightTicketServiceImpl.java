package com.web.puppylink.service;

import org.springframework.stereotype.Component;

import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.repository.FlightTicketRepository;

@Component("FlightTicketService")
public class FlightTicketServiceImpl implements FlightTicketService{
	private final FlightTicketRepository flightTicketRepository;
	
	public FlightTicketServiceImpl(FlightTicketRepository flightTicketRepository) {
		this.flightTicketRepository = flightTicketRepository;
	}

	@Override
	public FlightTicket submit(FlightTicket flightTicket) {
		return flightTicketRepository.save(flightTicket);
	}
}