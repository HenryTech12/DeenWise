package com.deenwise.demo.service;

import com.deenwise.demo.dto.AssessmentDetails;
import com.deenwise.demo.dto.LectureDTO;
import com.deenwise.demo.dto.TeacherDTO;
import com.deenwise.demo.mapper.AssessmentMapper;
import com.deenwise.demo.mapper.LectureMapper;
import com.deenwise.demo.mapper.TeacherMapper;
import com.deenwise.demo.model.AssessmentModel;
import com.deenwise.demo.model.TeacherModel;
import com.deenwise.demo.question.QuestionType;
import com.deenwise.demo.records.Lectures;
import com.deenwise.demo.response.LectureResponse;
import com.deenwise.demo.status.AssessmentStatus;
import com.deenwise.demo.status.LecturesStatus;
import com.deenwise.demo.repo.AssessmentRepository;
import com.deenwise.demo.repo.LecturesRepository;
import com.deenwise.demo.repo.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TeacherService
{

    @Autowired
    private AssessmentMapper assessmentMapper;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private LecturesRepository lecturesRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherMapper teacherMapper;

    public void uploadRecordedLectures(LectureDTO lectureDTO,byte[] video) {
        if(!Objects.isNull(lectureDTO)) {
            lectureDTO.setUploadedDate(LocalDate.now());
            lectureDTO.setStatus(LecturesStatus.NOT_STARTED.name());
            Lectures lectures = lectureMapper.convertToModel(lectureDTO);
            try {
                //lectures.setVideo(video);
            }
            catch(Exception ie) {
                log.error("ERROR: {}",ie.getMessage());
            }

            lecturesRepository.save(lectures);
            log.info("lecture info saved to db.");
        }
    }

    public List<LectureResponse> getLecturesInfo() {
        List<Lectures> lecturesList =
                        lecturesRepository.findAll();
        List<LectureResponse> lectureResponses = new ArrayList<>();
        if(!lecturesList.isEmpty()) {
            for(Lectures lectures : lecturesList) {
                lectureResponses.add(lectureMapper.convertToResponse(lectures));
            }
        }
        return lectureResponses;
    }


    public void saveAssessmentInfo(AssessmentDetails assessmentDetails) {
        if(!Objects.isNull(assessmentDetails)) {
            assessmentDetails.setCreated(LocalDate.now().toString());
            assessmentDetails.setStatus(AssessmentStatus.NOT_SUBMITTED.name());
            QuestionType[] questionTypes = QuestionType.values();
            for(QuestionType questionType : questionTypes) {
                if(assessmentDetails.getType().equalsIgnoreCase(questionType.name())) {
                    assessmentDetails.setType(questionType.name());
                    AssessmentModel assessmentModel = assessmentMapper.convertToModel(assessmentDetails);
                    assessmentRepository.save(assessmentModel);
                    log.info("assessment info saved to database");
                }
            }
        }
    }

    public List<AssessmentDetails> getExistingAssessments() {
        Optional<List<AssessmentModel>> optionalAssessmentModels
                = assessmentRepository.findByDuedateGreaterThan(LocalDate.now().toString());
        List<AssessmentDetails> assessmentDetails = null;
        if(optionalAssessmentModels.isPresent()) {
            List<AssessmentModel> assessmentModelList =
                     optionalAssessmentModels.orElse(new ArrayList<>());
           assessmentDetails = assessmentModelList.stream()
                    .map(assessmentMapper::convertToDTO).toList();
        }
        return assessmentDetails;
    }

    public TeacherDTO getTeacher(String email) {
        Optional<TeacherModel> optionalTeacherModel
                = teacherRepository.findByEmail(email);
        TeacherDTO teacherDTO = null;
        if(optionalTeacherModel.isPresent()) {
            TeacherModel teacherModel = optionalTeacherModel.orElse(new TeacherModel());
            teacherDTO = teacherMapper.convertToDTO(teacherModel);
            return teacherDTO;
        }
        return teacherDTO;
    }
}
