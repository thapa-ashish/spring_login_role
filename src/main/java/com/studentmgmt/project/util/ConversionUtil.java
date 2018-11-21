package com.studentmgmt.project.util;

import com.studentmgmt.project.dto.UserDTO;

public class ConversionUtil {
	
	
	public static com.studentmgmt.project.model.User convertUserDTOToUserEntity(UserDTO user) {
		com.studentmgmt.project.model.User userEntity= new com.studentmgmt.project.model.User();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(user.getPassword());
		
		return userEntity;
	}

}
