package com.web.puppylink.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.web.puppylink.model.Foundation;
import com.web.puppylink.repository.FoundationRepository;

@Component("foundationService")
public class FoundationServiceImpl implements FoundationService{
	private final FoundationRepository foundationRepository;
	
	public FoundationServiceImpl(FoundationRepository foundationRepository) {
		this.foundationRepository = foundationRepository;
	}

	@Override
	public List<Foundation> getFoundationAll() {
		return foundationRepository.findFoundationAllByOrderByBusinessNameDesc();
	}
}
