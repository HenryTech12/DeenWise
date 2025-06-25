package com.deenwise.demo.controller;

import com.deenwise.demo.dto.AssessmentDetails;
import com.deenwise.demo.dto.LectureDTO;
import com.deenwise.demo.dto.TeacherDTO;
import com.deenwise.demo.dto.UserDTO;
import com.deenwise.demo.response.LectureResponse;
import com.deenwise.demo.service.TeacherService;
import com.deenwise.demo.service.UserService;
import com.deenwise.demo.status.AssessmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
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
        setAssessmentStatus(model);
        return "teacher/assessment";
    }

    public void setAssessmentStatus(Model model) {
        model.addAttribute("e",AssessmentStatus.EXPIRED.name());
    }

    @PostMapping("/assessment/add")
    public String addAssessment(@ModelAttribute AssessmentDetails assessmentDetails, Model model) {
        System.out.println(assessmentDetails);
        teacherService.saveAssessmentInfo(assessmentDetails);
        List<AssessmentDetails> assessmentDetailsList = teacherService.getExistingAssessments();
        model.addAttribute("assessmentDetails", new AssessmentDetails());
        model.addAttribute("assessments", assessmentDetailsList);
        model.addAttribute("size", assessmentDetailsList.size());
        setAssessmentStatus(model);
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
        List<LectureResponse> lectureDTOList = teacherService.getLecturesInfo();
        model.addAttribute("lectures",lectureDTOList);
        model.addAttribute("lecture", new LectureResponse());
        return "teacher/video";
    }

    @PostMapping("/video/upload")
    public String uploadVideo(@ModelAttribute LectureDTO lecture, @RequestParam("file") MultipartFile videoFile, Model model, Principal principal) throws IOException {
        if(!Objects.isNull(videoFile)) {
            TeacherDTO teacherDTO = teacherService.getTeacher(principal.getName());
            lecture.setAssignedBy(teacherDTO.getName());

            byte[] videoBytes = videoFile.getBytes();
            System.out.println("bytes: "+ Arrays.toString(videoBytes));
            teacherService.uploadRecordedLectures(lecture,videoBytes);

            List<LectureResponse> lectureDTOList = teacherService.getLecturesInfo();
            model.addAttribute("lectures",lectureDTOList);
            model.addAttribute("lecture", new LectureResponse());
        }
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
