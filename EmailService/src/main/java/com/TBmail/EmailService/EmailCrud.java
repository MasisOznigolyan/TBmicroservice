package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Collections.Email;
import com.TBmail.EmailService.Repositories.EmailRepository;

@Service
public class EmailCrud {
	@Autowired
	private EmailRepository emailRepository;
	
	public void deleteAllEmails() {
		emailRepository.deleteAll();
	}
	public void createNewEmail(Email email) {
		emailRepository.save(email);
	}
}
