package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private int     commentNo;
    @Column(name = "letter")
    private String  letter;

    @ManyToOne
    @JoinColumn(name = "boardNo")
    private Board   boardNo;
    @ManyToOne
    @JoinColumn(name = "email")
    private Member  email;
    @Column(name = "regDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date    regDate;


}
