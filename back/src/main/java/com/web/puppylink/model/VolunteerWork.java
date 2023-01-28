package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "volunteerWork")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolunteerWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int volunteerNo;
    @Column(name = "depDate", columnDefinition = "출발일")
    @NotNull
    private String          depDate;
    @Column(name = "dest")
    @NotNull
    private String          dest;
    @Column(name = "destTime")
    @NotNull
    private String          destTime;
    @Column(name = "status")
    private String          status;
    @Column(name = "fileURL")
    private String          fileURL;
    @Column(name = "flightName")
    @NotNull
    private String          flightName;
    @Column(name = "regDate")
    private String          regDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member          email;

    @OneToOne
    @JoinColumn(name = "flightTicket_ticketNo")
    private FlightTicket    ticketNo;









}
