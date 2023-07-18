package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Collections.LastSent;
import com.TBmail.EmailService.Collections.UserEmail;
import com.TBmail.EmailService.Repositories.LastSentRepository;
@Service
public class LastSentCrud {

	@Autowired
	private LastSentRepository lastSentRepository;
	
	public void addLastSent(LastSent lastSent) {
		lastSentRepository.save(lastSent);
	}
	public LastSent findByUserEmailId(UserEmail userEmailId) {
		return lastSentRepository.findByUserEmailId(userEmailId);
	}
	public void deleteAllLastSent() {
		lastSentRepository.deleteAll();
	}
}
