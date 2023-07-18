package com.TBmail.EmailService.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TBmail.EmailService.Collections.User;
import com.TBmail.EmailService.Repositories.UsersRepository;
import com.TBmail.EmailService.Response.UserResponse;

@Service
public class UserService{
	
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public User createUser(User user) {
		
		return userRepository.save(user);
	}
	
	public UserResponse getUserById(String id) {
		
		User user=userRepository.findById(id).orElse(null);
		
		
		return modelMapper.map(user, UserResponse.class);
	}
	
	public UserResponse getUserByUserId(String uid) {
		
	    User user= userRepository.findByUserId(uid).orElse(null);
	    return modelMapper.map(user, UserResponse.class);
	}
	
	public List<User> getAllUsers() {
        return userRepository.findAll();
    }
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);
    }
	
	public void deleteAllUsers() {
	    userRepository.deleteAll();
	}
	
	/*
	 public User getUserByEmail(String eMail) {
	    return userRepository.findByEMail(eMail).orElse(null);
	 }
	 
	 */
	
	
	
	/*public User updateUser(User existingUser, User updatedUser) {
	    existingUser.setEMail(updatedUser.getEMail());
	    existingUser.setCategoryUrl(updatedUser.getCategoryUrl());
	    existingUser.setLastSentUrl(updatedUser.getLastSentUrl());
	    return userRepository.save(existingUser);
	}*/
	
	/*public User UpdateByUid(String uid, String field, List<String> value) {
		User user=getUserByUserId(uid);
		if(user!=null) {
			switch (field) {
            //case "eMail":
               // user.setEMail(value.get(0));
              //  break;
            case "categoryUrl":
               //user.setCategoryUrl(value);
                break;
            case "lastSentUrl":
            	user.setLastSentUrl(value.get(0));
            	break;
            default:
            	System.out.println("No such field as "+field);
                break;
			}
			return userRepository.save(user);
		}
		else {
			System.out.println("No such uid as "+uid);
			return null;
		}
	}*/
	/*public void updateLastSentById(String lastSentId, LastSent updatedLastSent) {
		lastSentRepository.updateLastSent(lastSentId, updatedLastSent);
	}*/
	
    
	 
	
	
	
	
}

