package org.deenwise.app.apis.mapper;

import org.deenwise.app.apis.dto.AssessmentDTO;
import org.deenwise.app.apis.model.AssessmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AssessmentMapper {

    @Autowired
    private ModelMapper mapper;

    public AssessmentDTO convertToDTO(AssessmentModel assessmentModel){
        if(!Objects.isNull(assessmentModel))
            return mapper.map(assessmentModel, AssessmentDTO.class);
        else
            return null;
    }

    public AssessmentModel convertToModel(AssessmentDTO assessmentDTO){
        if(!Objects.isNull(assessmentDTO))
            return mapper.map(assessmentDTO, AssessmentModel.class);
        else
            return null;
    }
}
