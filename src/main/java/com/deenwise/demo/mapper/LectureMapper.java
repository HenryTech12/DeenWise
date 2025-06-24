package com.deenwise.demo.mapper;

import com.deenwise.demo.dto.AdminDTO;
import com.deenwise.demo.dto.LectureDTO;
import com.deenwise.demo.model.AdminModel;
import com.deenwise.demo.records.Lectures;
import com.deenwise.demo.response.LectureResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LectureMapper {

    @Autowired
    private ModelMapper mapper;

    public LectureDTO convertToDTO(Lectures lectures) {
        if(!Objects.isNull(lectures))
            return mapper.map(lectures,LectureDTO.class);
        else
            return null;
    }

    public Lectures convertToModel(LectureDTO lectureDTO) {
        if(!Objects.isNull(lectureDTO))
            return mapper.map(lectureDTO, Lectures.class);
        else
            return null;
    }

    public LectureResponse convertToResponse(Lectures lectures) {
        if(!Objects.isNull(lectures))
            return mapper.map(lectures, LectureResponse.class);
        else
            return null;
    }

}
