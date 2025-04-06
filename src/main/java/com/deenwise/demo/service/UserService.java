package com.deenwise.demo.service;
import java.util.*;

import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.mapper.UserMapper;
import com.deenwise.demo.model.UserModel;
import com.deenwise.demo.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public void addUser(UserDTO userDTO) {
		if(!Objects.isNull(userDTO)) {
			if(userDTO.getRole() == null)
				userDTO.setRole("USER");
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userRepo.save(userMapper.convertToModel(userDTO));
			logger.info("added user details to database");
		}
	}

	public UserDTO getByEmail(String email) {
		Optional<UserModel> optionalUserModel = userRepo.findByEmail(email);
		if(optionalUserModel.isPresent()) {
			UserModel userModel = optionalUserModel.orElseThrow(() -> {throw new UsernameNotFoundException("user not found");});
			return userMapper.convertToDTO(userModel);
		}
		return null;
	}

	public void updatePassword(UserDTO userDTO, String newPassword) {
		if(!Objects.isNull(userDTO)) {
			Optional<UserModel> optionalUserModel = userRepo.findByEmail(userDTO.getEmail());
			if(optionalUserModel.isPresent()) {
				UserModel userModel = optionalUserModel.orElse(new UserModel());
				userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
				userRepo.save(userModel);
				logger.info("updated user password");
			}
		}
	}
}
