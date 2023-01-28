package com.web.puppylink.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int         favoriteNo;
    @ManyToOne
    @JoinColumn(name = "foundation_businessNo")
    private Foundation  businessNo;
    @ManyToOne
    @JoinColumn(name = "member_email")
    private Member      email;
}
