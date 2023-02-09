package com.web.puppylink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.puppylink.model.Volunteer.VolunteerBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class News {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "newsNo")
	 private int newsNo;
	 @Column(name = "subject", length = 500)
	 private String subject;
	 @Column(name = "content", length = 3000)
	 private String content;
	 @Column(name = "picURL", length = 3000)
	 private String picURL;
	 @Column(name = "newsURL", length = 300)
	 private String newsURL;
	 
	 

}
