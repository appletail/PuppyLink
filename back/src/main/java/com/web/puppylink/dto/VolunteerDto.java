package com.web.puppylink.dto;

import com.web.puppylink.model.FlightTicket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDto {
    private String dest;
    private String depTime;
    private String flightName;
    private String businessNo;
    private String email;
}
