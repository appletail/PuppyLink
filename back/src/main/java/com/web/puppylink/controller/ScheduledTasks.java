package com.web.puppylink.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.web.puppylink.model.FlightTicket;
import com.web.puppylink.repository.FlightTicketRepository;
import com.web.puppylink.service.VolunteerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledTasks {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss:SSS");
    
    private final FlightTicketRepository flightTicketRepository;
	private final VolunteerServiceImpl volunteerService;
    
    
    public ScheduledTasks(VolunteerServiceImpl volunteerService, FlightTicketRepository flightTicketRepository) {
		this.volunteerService = volunteerService;
    	this.flightTicketRepository = flightTicketRepository;
    }

//    @Scheduled(fixedRate = 1000)
    public void fixedRate() throws InterruptedException {
        log.info("1 시작");
        TimeUnit.SECONDS.sleep(5);
        log.info("1 fixedRate: 현재시간 - {}", formatter.format(LocalDateTime.now()));
    }

//    @Scheduled(fixedRate = 2000)
    public void fixedRate2() {
        log.info("2 fixedRate: 현재시간 - {}", formatter.format(LocalDateTime.now()));
    }
//
//    @Scheduled(fixedRate = 1000)
//    public void fixedRate3() {
//        log.info("fixedRate: 현재시간 - {}", formatter.format(LocalDateTime.now()));
//    }

//    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() throws InterruptedException {
        log.info("fixedDelay 시작시간 - {}", formatter.format(LocalDateTime.now()));
        TimeUnit.SECONDS.sleep(5);
        log.info("fixedDelay 종료시간 - {}", formatter.format(LocalDateTime.now()));
        Integer.parseInt("3");
    }

//    @Scheduled(fixedRate = 3000, initialDelay = 5000)
    public void fixedRateAndInitialDelay() {
        log.info(" fixedRateAndInitialDelay 현재시간 - {}", formatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "0 0/5 * 1/1 * ?")
    public void cronExpression() {
        List<FlightTicket> list = flightTicketRepository.getAfterArrive();
        System.out.println(list);
        if(!list.isEmpty()) {
	        for (FlightTicket ticket : list) {
	        	System.out.println(ticket);
	        	volunteerService.flightInfoDb(ticket.getTicketNo(), ticket.getFlight());
	        }
        }else {
        	return;
        }
    }
}