package com.codewithkk.blog.service;

import java.util.List;

import com.codewithkk.blog.payload.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	UserDto create(UserDto user);
	UserDto update(UserDto user,Integer userId);
	UserDto getUserById (Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
}
