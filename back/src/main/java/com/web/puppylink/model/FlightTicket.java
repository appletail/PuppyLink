package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "flightTicket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightTicket {

    @Id
    private String  ticketNo;
    @Column(name = "passengerName")
    @NotNull
    private String  passengerName;
    @Column(name = "bookingReference")
    @NotNull
    private String  bookingReference;
    @Column(name = "depCity")
    private String  depCity;
    @Column(name = "depDate")
    @Temporal(TemporalType.DATE)
    private Date    depDate;
    @Column(name = "arriveCity")
    @NotNull
    private String  arriveCity;
    @Column(name = "arriveDate")
    @Temporal(TemporalType.DATE)
    private Date    arriveDate;

}
