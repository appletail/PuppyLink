package com.web.puppylink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flightTicket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightTicket {

    @Id
    @Column(name = "ticketNo" , length = 100)
    private String  ticketNo;
    @Column(name = "passengerName" , length = 100)
    @NotNull
    private String  passengerName;
    @Column(name = "bookingReference" , length = 100)
    @NotNull
    private String  bookingReference;
    @Column(name = "depCity" , length = 100)
    private String  depCity;
    @Column(name = "depDate", length = 50)
    private String  depDate;
    @Column(name = "arriveCity" ,length = 100)
    @NotNull
    private String  arriveCity;
    @Column(name = "arriveDate" , length = 50)
    private String  arriveDate;
    @Column(name = "flight" ,length = 50)
    @NotNull
    private String  flight;
}
