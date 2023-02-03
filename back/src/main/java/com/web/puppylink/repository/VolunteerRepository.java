package com.web.puppylink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;

public interface VolunteerRepository  extends JpaRepository<Volunteer, String> {
	
	Volunteer findVolunteerByEmail(Member member);

}
