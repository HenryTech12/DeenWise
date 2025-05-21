package com.deenwise.demo.controller;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.service.StudentService;
import com.deenwise.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/student")
@Controller
public class StudentController
{

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;
    @GetMapping("/dashboard")
    public String accessDashBoard(Principal principal, Model model) {
        UserDTO userDTO = userService.getByEmail(principal.getName());
        model.addAttribute("name", userDTO.getName());
        return "dashboards/student";
    }

    @GetMapping("/settings")
    public String accessSettings() {
        return "settings";
    }

    @GetMapping("/assignment")
    public String assignmentPage(Model model) {
        model.addAttribute("assessments",studentService.getAssignments());
        model.addAttribute("assessmentDetails", new AssessmentDetails());
        return "studentsPages/assignment";
    }

    @GetMapping("/lessons")
    public String checkLesson() {
        return "studentsPages/lessons";
    }

    @GetMapping("/quran")
    public String getQuran() {
        return "studentsPages/quran";
    }


    @GetMapping("/profile")
    public String checkProfile(Model model, Principal principal) {
        model.addAttribute("student",  studentService.getByEmail(principal.getName()));
        model.addAttribute("user", new UserDTO());
        return "profile";
    }
}
