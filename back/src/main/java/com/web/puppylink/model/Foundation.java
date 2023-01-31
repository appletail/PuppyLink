package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @Column(name = "businessName")
    private String          businessName;
    @Column(name = "presidentName")
    private String          presidentName;
    @Column(name = "startDate")
    private String            startDate;
    @OneToOne
    @JoinColumn(name = "email")
    private Member          email;

}
