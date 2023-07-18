package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Repositories.UserCategoryRepository;

@Service
public class UserCategoryCrud {
	@Autowired
	private UserCategoryRepository userCategoryRepository;
	
	public UserCategory findByUserId(String userId) {
		return userCategoryRepository.findByUserId(userId);
	}
	public void deleteAllUserCategory() {
		userCategoryRepository.deleteAll();
	}
	
	public void createUserCategory(UserCategory userCategory) {
		userCategoryRepository.save(userCategory);
	}
}
