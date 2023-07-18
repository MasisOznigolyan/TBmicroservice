package com.TBmail.EmailService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.TBmail.EmailService.Collections.User;
import com.TBmail.EmailService.Response.UserResponse;
import com.TBmail.EmailService.Service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> getUserDetails(@PathVariable("id") String UserId){
		
		UserResponse user=userService.getUserByUserId(UserId);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
		
	}
}
