package com.studentmgmt.project.service;

import com.studentmgmt.project.model.User;

public interface UserService {
	
	public User findByEmail(String email);
	
	public void saveUser(User user);

}
