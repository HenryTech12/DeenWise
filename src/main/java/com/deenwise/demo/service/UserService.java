package com.deenwise.demo.service;
import java.time.LocalDate;
import java.util.*;

import com.deenwise.demo.dto.StudentDTO;
import com.deenwise.demo.dto.TeacherDTO;
import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.mapper.StudentMapper;
import com.deenwise.demo.mapper.TeacherMapper;
import com.deenwise.demo.mapper.UserMapper;
import com.deenwise.demo.model.StudentModel;
import com.deenwise.demo.model.TeacherModel;
import com.deenwise.demo.model.UserModel;
import com.deenwise.demo.repo.StudentRepository;
import com.deenwise.demo.repo.TeacherRepository;
import com.deenwise.demo.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService
{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private TeacherMapper teacherMapper;
	public void addUser(UserDTO userDTO) {
		if(!Objects.isNull(userDTO)) {
			if (Objects.deepEquals(userDTO.getPassword(), userDTO.getCpassword())) {
				if (Objects.isNull(userDTO.getRole())) {
					userDTO.setRole("ROLE_USER");
				}
				StudentDTO studentDTO = userMapper.convertToStudent(userDTO);
				studentRepository.save(studentMapper.convertToModel(studentDTO));
				log.info("added student data to db.");

				userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
				userRepo.save(userMapper.convertToModel(userDTO));
				log.info("added user details to database");
			}
		}
	}


	public void saveTeacher(UserDTO userDTO) {
		if(!Objects.isNull(userDTO)) {
			TeacherDTO teacherDTO = userMapper.convertToTeacher(userDTO);
			teacherDTO.setJoined(LocalDate.now().toString());
			teacherRepository.save(teacherMapper.convertToModel(teacherDTO));
			log.info("teacher details added to db.");
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

	public void updatePassword(UserDTO userDTO, String newPassword, String email) {
		if(!Objects.isNull(userDTO)) {
			Optional<UserModel> optionalUserModel = userRepo.findByEmail(email);
			if(optionalUserModel.isPresent()) {
				if(userDTO.getPassword().equals(userDTO.getCpassword())){
					UserModel userModel = optionalUserModel.orElse(new UserModel());
					if(passwordEncoder.matches(userDTO.getCurrentPassword(), userModel.getPassword())) {
						userModel.setPassword(passwordEncoder.encode(userDTO.getPassword()));
						System.out.println("updated password");
						userRepo.save(userModel);
						log.info("updated user password");
					}
				}
			}
		}
	}

	public String convertToJson(String data) {
		Map<String,String> mydata = new HashMap<>();
		mydata.put("name",data);
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(mydata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
