package com.deenwise.demo.controller;

import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Objects;

@RequestMapping("/teacher")
@Controller
public class TeacherController
{

    @Autowired
    private UserService userService;
    @GetMapping("/dashboard")
    public String dashbaord(Principal principal, Model model) {
        UserDTO userDTO = userService.getByEmail(principal.getName());
        if(!Objects.isNull(userDTO))
            model.addAttribute("name", userDTO.getName());
        return "afterloginpages/teacherdashboard";
    }

    @GetMapping("/store")
    @ResponseBody
    public String testSave() {
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("Teacher");
        userDTO.setPassword("5679");
        userDTO.setEmail("johndoeteacher@gmail.com");
        userDTO.setName("Mr John");
        userService.addUser(userDTO);

        return "admin registered teacher";
    }
}
