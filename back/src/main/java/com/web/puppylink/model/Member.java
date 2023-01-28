package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    @Id
    private String              email;
    @Column(name = "password")
    @JsonIgnore
    @ToString.Exclude
    private String	            password;
    @Column(name = "name")
    private String	            name;
    @Column(name = "phone")
    private String	            phone;
    @Column(name = "nickName")
    private String	            nickName;

    @OneToOne
    @JoinColumn(name = "foundation_businessNo")
    private Foundation          businessNo;
    @Column(name = "joinDate")
    private Date                joinDate;

    @OneToMany(mappedBy = "board")
    private List<Board>         boards;

    @OneToMany(mappedBy = "comments")
    private List<Comments>      commentsList;

    @OneToMany(mappedBy = "volunteerWork")
    private List<VolunteerWork> volunteerList;

    @OneToMany(mappedBy = "favorite")
    private List<Favorite>      favoriteList;

}
