//package com.web.puppylink.model;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.Lob;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.OrderBy;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.CreationTimestamp;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@ApiModel
//@Table(name = "Location")
//public class Location {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
////	@Column(nullable = false, length = 200)
////	private String content;
//	
//	@ManyToOne
//	@JoinColumn(name="ticketNo")
//	private FlightTicket ticketNo;
//	
//	//위도 경도 필드가 있어야함
//    @ApiModelProperty(name = "tmp 항공기 위도", required = true, example = "0")
//    private Double tmpLat;
//    @ApiModelProperty(name = "tmp 항공기 경도", required = true, example = "0")
//    private Double tmpLng;
//	
//	//삭제하려면 cascade
//}
