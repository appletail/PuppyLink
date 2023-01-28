package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "foundation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int             businessNo;
    @Column(name = "businessName")
    private String          businessName;
    @Column(name = "presidentName")
    private String          presidentName;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date            startDate;
    @OneToMany(mappedBy = "favorite")
    private List<Favorite>  favoriteList;
}
