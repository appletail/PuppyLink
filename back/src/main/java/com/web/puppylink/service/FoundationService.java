package com.web.puppylink.service;

import java.util.List;
import java.util.Optional;

import com.web.puppylink.dto.FoundationDto;
import com.web.puppylink.dto.MemberDto;
import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.File.FileRequest;

public interface FoundationService {
	List<Foundation> getFoundationAll();
	Member signup(MemberDto member);
	Foundation submitProfile(FileRequest file);
	Foundation createDescription(String email, String description);
}
