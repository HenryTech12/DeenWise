package com.deenwise.demo.service;
import com.deenwise.demo.dto.*;

import java.time.LocalDate;
import java.util.*;

import com.deenwise.demo.model.AssessmentModel;
import com.deenwise.demo.model.StudentModel;
import com.deenwise.demo.model.TeacherModel;
import com.deenwise.demo.question.QuestionType;
import com.deenwise.demo.repo.*;
import com.deenwise.demo.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService
{
	private static final Logger log = LoggerFactory.getLogger(StudentService.class);
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private AssessmentRepository assessmentRepository;
	@Autowired
	private AssessmentMapper assessmentMapper;
	public void addStudent(StudentDTO studentDTO) {
		if(!Objects.isNull(studentDTO)) {
			studentDTO.setJoined(LocalDate.now().toString());
			studentRepo.save(studentMapper.convertToModel(studentDTO));
		}
	}

	public StudentDTO getByEmail(String email) {
		System.out.println("email fetched is cached.");
		Optional<StudentModel> studentModel = studentRepo.findByEmail(email);
		if(studentModel.isPresent())
			return studentMapper.convertToDTO(studentModel.orElse(new StudentModel()));
		else
			return null;
	}

	public List<AssessmentDetails> getAssignments() {
		Optional<List<AssessmentModel>> optionalAssessmentDetails
							= assessmentRepository.findByTypeAndDuedateGreaterThan(QuestionType.ASSIGNMENT.name(), LocalDate.now().toString());
		List<AssessmentDetails> assessmentDetailsList = null;
		if(optionalAssessmentDetails.isPresent()) {
			List<AssessmentModel> assessmentModelList = optionalAssessmentDetails.orElse(new ArrayList<>());
			assessmentDetailsList = assessmentModelList.stream().map(assessmentMapper::convertToDTO).toList();
		}
		return assessmentDetailsList;
	}

	public void updateStudent(String email, StudentDTO studentDTO) {
		Optional<StudentModel> optionalStudentModel =
				studentRepo.findByEmail(email);
		if(optionalStudentModel.isPresent()) {
			StudentModel studentModel = optionalStudentModel.orElse(new StudentModel());
			StudentModel updatedStudentModel = studentMapper.convertToModel(studentDTO);
			updatedStudentModel.setId(studentModel.getId());
			studentRepo.save(updatedStudentModel);
			log.info("student data updated.");
		}
	}



}
