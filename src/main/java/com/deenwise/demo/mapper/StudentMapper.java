package com.deenwise.demo.mapper;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentMapper
{
	@Autowired
	private ModelMapper mapper;
	
	public StudentDTO convertToDTO(StudentModel studentModel) {
		if(!Objects.isNull(studentModel)) 
			return mapper.map(studentModel,StudentDTO.class);
		else
		    return new StudentDTO();
	}
   public StudentModel convertToModel(StudentDTO studentDTO) {
	   if(!Objects.isNull(studentDTO)) 
		   return mapper.map(studentDTO,StudentModel.class);
	   else
	       return null;
   }

}
