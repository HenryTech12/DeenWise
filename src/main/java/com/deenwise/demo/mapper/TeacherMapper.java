package com.deenwise.demo.mapper;

import com.deenwise.demo.dto.TeacherDTO;
import com.deenwise.demo.model.TeacherModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeacherMapper
{
    @Autowired
    private ModelMapper mapper;

    public TeacherDTO convertToDTO(TeacherModel teacherModel) {
        if(!Objects.isNull(teacherModel))
            return mapper.map(teacherModel, TeacherDTO.class);
        else
            return null;
    }

    public TeacherModel convertToModel(TeacherDTO teacherDTO) {
        if(!Objects.isNull(teacherDTO))
            return mapper.map(teacherDTO, TeacherModel.class);
        else
            return null;
    }
}
