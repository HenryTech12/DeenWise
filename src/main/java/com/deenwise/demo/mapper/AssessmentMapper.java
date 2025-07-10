package com.deenwise.demo.mapper;

import com.deenwise.demo.dto.AdminDTO;
import com.deenwise.demo.dto.AssessmentDetails;
import com.deenwise.demo.model.AdminModel;
import com.deenwise.demo.model.AssessmentModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AssessmentMapper {

    @Autowired
    private ModelMapper mapper;

    public AssessmentDetails convertToDTO(AssessmentModel assessmentModel) {
        if(!Objects.isNull(assessmentModel))
            return  mapper.map(assessmentModel,AssessmentDetails.class);
        else
            return null;
    }

    public AssessmentModel convertToModel(AssessmentDetails assessmentDetails) {
        if(!Objects.isNull(assessmentDetails))
            return mapper.map(assessmentDetails, AssessmentModel.class);
        else
            return null;
    }

}
