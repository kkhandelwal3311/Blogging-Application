package com.codewithkk.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithkk.blog.config.AppConstants;
import com.codewithkk.blog.entities.Role;
import com.codewithkk.blog.entities.User;
import com.codewithkk.blog.exception.ResourceNotFoundException;
import com.codewithkk.blog.payload.UserDto;
import com.codewithkk.blog.repositories.RoleRepo;
import com.codewithkk.blog.repositories.UserRepo;
import com.codewithkk.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto create(UserDto userDto) {
		// TODO Auto-generated method stub

		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto update(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(" User ", " id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(" User ", " Id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(" User ", " Id ", userId));
		this.userRepo.delete(user);

	}

	public User dtoToUser(UserDto userDto) {

		User user = modelMapper.map(userDto, User.class);

//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;
	}

	public UserDto userToDto(User user) {

		UserDto userDto = modelMapper.map(user, UserDto.class);
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());

		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = modelMapper.map(userDto, User.class);
//		Encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
	Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
	user.getRoles().add(role);
	User newUser = userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
