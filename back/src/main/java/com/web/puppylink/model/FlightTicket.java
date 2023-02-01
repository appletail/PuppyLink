package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "flightTicket")
@Data
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

}
