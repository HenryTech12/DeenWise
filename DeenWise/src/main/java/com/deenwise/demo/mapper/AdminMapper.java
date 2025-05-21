package com.deenwise.demo.mapper;

import com.deenwise.demo.dto.AdminDTO;
import com.deenwise.demo.model.AdminModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminMapper
{
    @Autowired
    private ModelMapper mapper;

    public AdminDTO convertToDTO(AdminModel adminModel) {
        if(!Objects.isNull(adminModel))
            return  mapper.map(adminModel,AdminDTO.class);
        else
            return null;
    }

    public AdminModel convertToModel(AdminDTO adminDTO) {
        if(!Objects.isNull(adminDTO))
            return mapper.map(adminDTO, AdminModel.class);
        else
            return null;
    }
}
