package org.deenwise.app.apis.controller;

import org.deenwise.app.apis.dto.AssessmentDTO;
import org.deenwise.app.apis.dto.LectureDTO;
import org.deenwise.app.apis.pagination.AppPageRequest;
import org.deenwise.app.apis.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/deen/api/user/teacher")
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/assessment/create")
    public ResponseEntity<AssessmentDTO> createAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        return new ResponseEntity<>(teacherService.createAssessment(assessmentDTO), HttpStatus.OK);
    }

    @PostMapping("/lecture/create")
    public ResponseEntity<LectureDTO> createLecture(@RequestBody LectureDTO lectureDTO) {
        return new ResponseEntity<>(teacherService.createLecture(lectureDTO),HttpStatus.OK);
    }

    @GetMapping("/lecture/get/{id}")
    public ResponseEntity<LectureDTO> getLectureByID(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getLectureByID(id),HttpStatus.OK);
    }

    @GetMapping("/assessment/get/{id}")
    public ResponseEntity<AssessmentDTO> getAssessmentByID(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.getAssessmentById(id),HttpStatus.OK);
    }

    @GetMapping("/assessment/all")
    public ResponseEntity<Page<AssessmentDTO>> getAssessments(@RequestBody AppPageRequest request) {
        return new ResponseEntity<>(teacherService.getAssessments(request),HttpStatus.OK);
    }

    @GetMapping("/lecture/all")
    public ResponseEntity<Page<LectureDTO>> getLectures(AppPageRequest request) {
        return new ResponseEntity<>(teacherService.getLectures(request),HttpStatus.OK);
    }

    @PutMapping("/assessment/{id}/update")
    public ResponseEntity<AssessmentDTO> updateAssessmentByID(@PathVariable Long id, @RequestBody AssessmentDTO assessmentDTO) {
        return new ResponseEntity<>(teacherService.updateAssessmentByID(id,assessmentDTO),HttpStatus.OK);
    }

    @PutMapping("/lecture/{id}/update")
    public ResponseEntity<LectureDTO> updateLectureByID(@PathVariable Long id, @RequestBody LectureDTO lectureDTO) {
        return new ResponseEntity<>(teacherService.updateLectureByID(id,lectureDTO), HttpStatus.OK);
    }

    @DeleteMapping("/lecture/delete/{id}")
    public ResponseEntity<LectureDTO> deleteLectureByID(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.deleteLectureWithID(id),HttpStatus.OK);
    }

    @DeleteMapping("/assessment/delete/{id}")
    public ResponseEntity<AssessmentDTO> deleteAssessmentByID(@PathVariable Long id) {
        return new ResponseEntity<>(teacherService.deleteAssessmentWithID(id),HttpStatus.OK);
    }
}
