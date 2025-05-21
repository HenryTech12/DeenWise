package com.deenwise.demo.controller;

import com.deenwise.demo.dto.AssessmentDetails;
import com.deenwise.demo.dto.LectureDTO;
import com.deenwise.demo.dto.TeacherDTO;
import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.service.TeacherService;
import com.deenwise.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RequestMapping("/teacher")
@Controller
public class TeacherController
{

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        UserDTO userDTO = userService.getByEmail(principal.getName());
        if(!Objects.isNull(userDTO))
            model.addAttribute("name", userDTO.getName());
        return "dashboards/teacher";
    }

    @GetMapping("/assessment")
    public String checkAssessment(Model model) {
        model.addAttribute("assessmentDetails", new AssessmentDetails());
        return "teacher/assessment";
    }

    @PostMapping("/assessment/add")
    public String addAssessment(@ModelAttribute AssessmentDetails assessmentDetails, Model model) {
        System.out.println(assessmentDetails);
        teacherService.saveAssessmentInfo(assessmentDetails);
        List<AssessmentDetails> assessmentDetailsList = teacherService.getExistingAssessments();
        model.addAttribute("assessmentDetails", new AssessmentDetails());
        model.addAttribute("assessments", assessmentDetailsList);
        return "teacher/assessment";
    }

    @GetMapping("/quran")
    public String quranPage() {
        return "teacher/quran";
    }

    @GetMapping("/test")
    public String getTestPage(Model model) {
        return "teacher/tassignments";
    }

    @GetMapping("/chat")
    public String getChatPage() {
        return "teacher/tchat";
    }

    @GetMapping("/courses")
    public String accessCoursePage() {
        return "teacher/tcourses";
    }

    @GetMapping("/video")
    public String videoLessonPage(Model model) {
        model.addAttribute("lecture", new LectureDTO());
        return "teacher/video";
    }

    @PostMapping("/video/upload")
    public String uploadVideo(@ModelAttribute LectureDTO lecture, Model model) {
        teacherService.uploadRecordedLectures(lecture);
        List<LectureDTO> lectureDTOList = teacherService.getAllLectures();
        model.addAttribute("lectures",lectureDTOList);
        model.addAttribute("lecture", new LectureDTO());
        return "teacher/video";
    }

    @GetMapping("/settings")
    public String accessSettings(Principal principal, Model model) {
        TeacherDTO teacherDTO = teacherService.getTeacher(principal.getName());
        model.addAttribute("teacher",teacherDTO);
        return "settings";
    }

    @GetMapping("/profile")
    public String checkProfile() {
        return "profile";
    }
}
