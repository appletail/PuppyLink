package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "foundation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessNo")
    private int             businessNo;
    @Column(name = "profileURL")
    private String          profileURL;
    @Column(name = "businessName" , length = 100)
    @NotNull
    private String          businessName;
    @Column(name = "presidentName" , length = 100)
    private String          presidentName;
    @Column(name = "startDate" , length = 50)
    private String          startDate;
    @OneToOne
    @JoinColumn(name = "members_email", referencedColumnName = "email")
    private Member          email;

}
