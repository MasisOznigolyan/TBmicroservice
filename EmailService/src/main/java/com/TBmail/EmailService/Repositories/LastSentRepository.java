package com.TBmail.EmailService.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.TBmail.EmailService.LastSent;
import com.TBmail.EmailService.UserEmail;

public interface LastSentRepository extends MongoRepository<LastSent, String> {
    List<LastSent> findAll();
    LastSent findByLastSentId(String lastSentId);
    @SuppressWarnings("unchecked")
    LastSent save(LastSent lastSentUrl);
    void deleteById(String id);
    void deleteAll();
    //LastSent findByeMailId(String EmailId);
    LastSent findByUserEmailId(UserEmail userEmailId);
	
  
}