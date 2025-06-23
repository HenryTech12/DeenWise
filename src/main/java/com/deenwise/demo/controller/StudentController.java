package com.deenwise.demo.controller;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.service.StudentService;
import com.deenwise.demo.service.TeacherService;
import com.deenwise.demo.service.UserService;
import com.deenwise.demo.status.AssessmentStatus;
import com.deenwise.demo.status.LecturesStatus;
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
    @Autowired
    private TeacherService teacherService;
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
        List<AssessmentDetails> assessmentDetailsList = studentService.getAssignments();
        assessmentDetailsList.forEach(System.out::println);
        model.addAttribute("assessments", assessmentDetailsList);
        model.addAttribute("assessmentDetails", new AssessmentDetails());
        setAssessmentStatus(model);
        return "studentsPages/assignment";
    }

    public void setAssessmentStatus(Model model) {
        model.addAttribute("ns", AssessmentStatus.NOT_SUBMITTED.name());
        model.addAttribute("p",AssessmentStatus.PENDING.name());
        model.addAttribute("g",AssessmentStatus.GRADED.name());
    }
    @GetMapping("/lessons")
    public String checkLesson(Model model) {
        model.addAttribute("lectures",teacherService.getAllLectures());
        model.addAttribute("lecture", new LectureDTO());
        setLessonStatus(model);
        return "studentsPages/lessons";
    }

    public void setLessonStatus(Model model) {
        model.addAttribute("ip", LecturesStatus.IN_PROGRESS.name());
        model.addAttribute("ns", LecturesStatus.NOT_STARTED.name());
        model.addAttribute("c",LecturesStatus.COMPLETED.name());
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
