package com.TBmail.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Repositories.NewsRepository;

@Service
public class NewsCrud {
	
	@Autowired
	private NewsRepository newsRepository;
	
	public void addNews(News news) {
		newsRepository.save(news);
	}
	
	public void deleteAllNews() {
		newsRepository.deleteAll();
	}
}
