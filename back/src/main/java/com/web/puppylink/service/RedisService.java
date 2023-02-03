package com.web.puppylink.service;

import com.web.puppylink.dto.MailDto;

public interface RedisService {

    void saveMail(MailDto mail) throws Exception;
    void saveToken() throws Exception;
    boolean getConfirmAuthByEamil(String email, String Auth) throws Exception;
}
