package com.justinwilmot.capstone.app.service;

import com.justinwilmot.capstone.app.entity.User;
import com.justinwilmot.capstone.app.user.CrmUser;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);
	
	

	public void save(CrmUser crmUser);



	public void saveUser(User theUser);
}
