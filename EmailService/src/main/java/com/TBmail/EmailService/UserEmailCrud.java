package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Repositories.UserEmailRepository;

@Service
public class UserEmailCrud {

	@Autowired
	private UserEmailRepository userEmailRepository;
	
	public UserEmail findByUserId(String userId) {
		return userEmailRepository.findByUserId(userId);
	}
	
	public void deleteAllUserEmail() {
		userEmailRepository.deleteAll();
	}
	
	public void addUserEmail(UserEmail userEmail) {
		userEmailRepository.save(userEmail);
	}
}
