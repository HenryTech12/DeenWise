package com.deenwise.demo.service;
import com.deenwise.demo.dto.*;
import java.util.*;
import com.deenwise.demo.repo.*;
import com.deenwise.demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService
{
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private StudentMapper studentMapper;
	public void addStudent(StudentDTO studentDTO) {
		if(!Objects.isNull(studentDTO)) {
			studentRepo.save(studentMapper.convertToModel(studentDTO));
		}
	}

	public void getByEmail(String email) {

	}

}
