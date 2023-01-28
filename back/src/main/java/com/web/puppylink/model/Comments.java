package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comments {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentNo")
    private String  commentNo;
    @Column(name = "letter")
    private String  letter;

    @ManyToOne
    @JoinColumn(name = "board_boardNo")
    private Board   boardNo;
    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member  email;
    @Column(name = "regDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date    regDate;


}
