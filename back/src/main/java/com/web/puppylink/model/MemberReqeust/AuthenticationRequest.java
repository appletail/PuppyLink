package com.web.puppylink.model.MemberReqeust;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    
    String email;
    String password;
    String name;
    
}
