package com.web.puppylink.service;

import java.util.List;

import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;

public interface FoundationService {
	List<Foundation> getFoundationAll();
	Member signup(MemberDto member);
	Foundation submitProfile(String nickName, String imagePath);
}
