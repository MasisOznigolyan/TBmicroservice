package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Repositories.NewsCategoryRepository;


@Service
public class NewsCategoryCrud {
	
	@Autowired
	private NewsCategoryRepository newsCategoryRepository;
	
	public void addNewsCategory(NewsCategory category ) {
		newsCategoryRepository.save(category);
	}
	
	public void deleteAllNewsCategory() {
		newsCategoryRepository.deleteAll();
	}
}
