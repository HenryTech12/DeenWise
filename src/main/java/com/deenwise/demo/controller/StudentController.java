package com.deenwise.demo.controller;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@RequestMapping("/student")
@Controller
public class StudentController
{

    @Autowired
    private UserService userService;
    @GetMapping("/dashboard")
    public String accessDashBoard(Principal principal, Model model) {
        UserDTO userDTO = userService.getByEmail(principal.getName());
        model.addAttribute("name", userDTO.getName());
        return "afterloginpages/studentdashboard";
    }
}
