package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    @Id
    @Column(name = "email")
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

    @Column(name = "activated")
    private boolean             activated;

    @ManyToMany
    @JoinTable(
            name = "members_authority",
            joinColumns = {@JoinColumn(name = "email", referencedColumnName = "email")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority>      authorities;

    @Column(name = "joinDate")
    private Date                joinDate;

}
