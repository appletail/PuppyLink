package com.web.puppylink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.web.puppylink.model.Foundation;
import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, String>{
	List<Volunteer> findVolunteerByBusinessNo(Foundation foundation);
	List<Volunteer> findVolunteerByEmail(Member member);
	Optional<Volunteer> findVolunteerByVolunteerNo(int volunteerNo);
	Optional<Volunteer> deleteVolunteerByVolunteerNo(int volunteerNo);
	
	List<Volunteer> findVolunteerByBusinessNoAndStatus(Foundation foundation, String status);
	List<Volunteer> findVolunteerByEmailAndStatus(Member member, String status);
}
