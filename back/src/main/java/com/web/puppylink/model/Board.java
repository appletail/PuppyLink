package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
