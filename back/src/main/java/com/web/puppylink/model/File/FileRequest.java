package com.web.puppylink.model.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {

    private String nickName;
    private String imagePath;
    private int volunteerNo;
    private String ticketType;
}
