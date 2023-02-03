package com.web.puppylink.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.web.puppylink.model.Member;
import com.web.puppylink.model.Volunteer;
import com.web.puppylink.repository.MemberRepository;
import com.web.puppylink.repository.VolunteerRepository;

@Component("volunteerService")
public class VolunteerServiceImpl {

	private final MemberRepository memberRepository;
	private final VolunteerRepository volunteerRepository;
	
	public VolunteerServiceImpl(
			MemberRepository memberRepository,
			VolunteerRepository volunteerRepository) {
		this.memberRepository = memberRepository;
		this.volunteerRepository = volunteerRepository;
	}
	
	public Volunteer submitFile(String nickName, String imagePath) {
		
		// 닉네임으로 멤버 찾기
		Member member = memberRepository.findByNickName(nickName);
//		System.out.println("email= " + email);
		
		// 멤버로 volunteer 찾기
		Volunteer vol = volunteerRepository.findVolunteerByEmail(member);
		
		// fileURL 업데이트
		vol.setFileURL(imagePath);
		volunteerRepository.save(vol);
		
		return vol;
	}

}
