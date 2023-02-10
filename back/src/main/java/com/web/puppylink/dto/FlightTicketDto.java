package com.web.puppylink.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightTicketDto {
	@ApiModelProperty(dataType = "int", example = "1")
	private int  volunteerNo;
	@ApiModelProperty(dataType = "String", example = "1802326239336")
	private String  ticketNo;
	@ApiModelProperty(dataType = "String", example = "RO/MYUNGSUK MR (KE11381593****)")
    private String  passengerName;
	@ApiModelProperty(dataType = "String", example = "66788570 (KG9W48)")
    private String  bookingReference;
	@ApiModelProperty(dataType = "String", example = "ICN")
    private String  depCity;
	@ApiModelProperty(dataType = "String", example = "2019-01-22 20:10:00")
    private String  depDate;
	@ApiModelProperty(dataType = "String", example = "BKK")
    private String  arriveCity;
	@ApiModelProperty(dataType = "String", example = "2019-01-23 00:20:00")
    private String  arriveDate;
	@ApiModelProperty(dataType = "String", example = "KE 659")
    private String  flight;
}
