package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "email" , length = 100)
    private String              email;
    @Column(name = "password", length = 100)
    @JsonIgnore
    @ToString.Exclude
    private String	            password;
    @Column(name = "name" , length = 100)
    @NotNull
    private String	            name;
    @Column(name = "phone", length = 50)
    @NotNull
    private String	            phone;

    @Column(name = "nickName" , length = 100)
    @NotNull
    private String	            nickName;

    @Column(name = "activated")
    @NotNull
    private boolean             activated;

    @ManyToMany
    @JoinTable(
            name = "members_authority",
            joinColumns = {@JoinColumn(name = "members_email", referencedColumnName = "email")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority>      authorities;

    @Column(name = "joinDate", length = 50)
    @NotNull
    private String              joinDate;
    
    @Lob
    private String refreshToken;
    
    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }

}
