package com.web.puppylink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardNo")
    private int     boardNo;
    @Column(name = "subject", length = 500)
    @NotNull
    private String  subject;
    @Column(name = "contents", length = 2000)
    private String  contents;
    @Column(name = "pictureURL")
    private String  pictureURL;
    @Column(name = "likes" , length = 100)
    @ColumnDefault("'0'")
    private String  likes;
    @Column(name = "regDate", length = 50)
    private String  regDate;
    @ManyToOne
    @JoinColumn(name = "members_email", referencedColumnName = "email")
    private Member  email;

}
