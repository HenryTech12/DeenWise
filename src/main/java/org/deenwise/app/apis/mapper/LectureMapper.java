package org.deenwise.app.apis.mapper;

import org.deenwise.app.apis.dto.LectureDTO;
import org.deenwise.app.apis.model.LectureModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LectureMapper {

    @Autowired
    private ModelMapper mapper;

    public LectureDTO convertToDTO(LectureModel lectureModel) {
        if(!Objects.isNull(lectureModel))
            return mapper.map(lectureModel, LectureDTO.class);
        else
            return null;
    }

    public LectureModel convertToModel(LectureDTO lectureDTO) {
        if(!Objects.isNull(lectureDTO))
            return mapper.map(lectureDTO, LectureModel.class);
        else
            return null;
    }
}
