package com.deenwise.demo.mapper;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMapper
{
	@Autowired
	private ModelMapper mapper;
	
	public UserDTO convertToDTO(UserModel userModel) {
		if(!Objects.isNull(userModel)) 
			return mapper.map(userModel,UserDTO.class);
		else
		    return null;
	}
	
	public UserModel convertToModel(UserDTO userDTO) {
		if(!Objects.isNull(userDTO)) 
			return mapper.map(userDTO, UserModel.class);
		else
		    return null;
	}
}
