package com.deenwise.demo.controller;

import com.deenwise.demo.dto.AdminDTO;
import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/admin")
@Controller
public class AdminController
{

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public String createAdmin() {
        AdminDTO adminDTO = new AdminDTO();
        return "admin created successfully";
    }

    @GetMapping("/teacher/add")
    @ResponseBody
    public String addTeacher() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("ROLE_TEACHER");
        userDTO.setPassword("odunayo@12");
        userDTO.setEmail("johndoeteacher@gmail.com");
        userDTO.setCpassword("odunayo@12");
        userDTO.setName("Mr John");
        userService.addUser(userDTO);

        userService.saveTeacher(userDTO);
        return "admin registered teacher";
    }

}
