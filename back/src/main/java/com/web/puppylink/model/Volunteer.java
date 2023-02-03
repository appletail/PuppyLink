package com.web.puppylink.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteerNo")
    private int volunteerNo;
    @Column(name = "depTime" ,length = 100)
    @NotNull
    private String          depTime;
    @Column(name = "dest" , length = 100)
    @NotNull
    private String          dest;
    @Column(name = "status" ,length = 100)
    private String          status;
    @Column(name = "fileURL" , length = 100)
    private String          fileURL;
    @Column(name = "flightName" , length = 100)
    @NotNull
    private String          flightName;
    @Column(name = "regDate")
    private String          regDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "members_email", referencedColumnName = "email")
    private Member          email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticketNo")
    private FlightTicket    ticketNo;

    @OneToOne
    @JoinColumn(name = "foundation_businessNo", referencedColumnName = "businessNo")
    private Foundation      businessNo;









}
