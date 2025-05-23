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
import com.deenwise.demo.repo.AssessmentRepository;
import com.deenwise.demo.repo.LecturesRepository;
import com.deenwise.demo.repo.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public void uploadRecordedLectures(LectureDTO lectureDTO) {
        if(!Objects.isNull(lectureDTO)) {
            lectureDTO.setUploadedDate(LocalDate.now());
            Lectures lectures = lectureMapper.convertToModel(lectureDTO);

            MultipartFile videoFile = lectureDTO.getVideo();
            try {
                lectures.setVideo(videoFile.getBytes());
            }
            catch(IOException ie) {
                log.error("ERROR: {}",ie.getMessage());
            }
            lecturesRepository.save(lectures);
            log.info("lecture info saved to db.");
        }
    }

    public List<LectureDTO> getAllLectures() {
        List<Lectures> lecturesList =
                        lecturesRepository.findAll();
        List<LectureDTO> lectureDTOList = new ArrayList<>();
        if(!lecturesList.isEmpty()) {
            for(Lectures lectures : lecturesList) {
                lectureDTOList.add(lectureMapper.convertToDTO(lectures));
            }
        }
        return lectureDTOList;
    }


    public void saveAssessmentInfo(AssessmentDetails assessmentDetails) {
        if(!Objects.isNull(assessmentDetails)) {
            assessmentDetails.setCreated(LocalDate.now().toString());

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
