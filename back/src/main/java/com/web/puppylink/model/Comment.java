package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentNo")
    private int     commentNo;
    @Column(name = "letter", length = 1000)
    private String  letter;

    @ManyToOne
    @JoinColumn(name = "board_boardNo", referencedColumnName = "boardNo")
    private Board   boardNo;
    @ManyToOne
    @JoinColumn(name = "members_email", referencedColumnName = "email")
    private Member  email;
    @Column(name = "regDate", length = 50)
    private String  regDate;


}
