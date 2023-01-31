package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardNo")
    private int     boardNo;
    @Column(name = "subject")
    private String  subject;
    @Column(name = "contents")
    private String  contents;
    @Column(name = "pictureURL")
    private String  pictureURL;
    @Column(name = "likes")
    private String  likes;
    @Column(name = "regDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @ManyToOne
    @JoinColumn(name = "email")
    private Member  email;

}
