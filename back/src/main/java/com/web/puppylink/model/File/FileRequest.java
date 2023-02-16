package com.web.puppylink.model.File;

import com.web.puppylink.model.MemberReqeust.AuthenticationRequest;
import com.web.puppylink.model.MemberReqeust.AuthenticationRequest.AuthenticationRequestBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileRequest {

    private String nickName;
    private String imagePath;
    private int volunteerNo;
    private String ticketType;
    private int boardNo;

}
