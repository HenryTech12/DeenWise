package org.deenwise.app.apis.service;

import lombok.extern.slf4j.Slf4j;
import org.deenwise.app.apis.dto.AssessmentDTO;
import org.deenwise.app.apis.dto.LectureDTO;
import org.deenwise.app.apis.mapper.AssessmentMapper;
import org.deenwise.app.apis.mapper.LectureMapper;
import org.deenwise.app.apis.model.AssessmentModel;
import org.deenwise.app.apis.model.LectureModel;
import org.deenwise.app.apis.pagination.AppPageRequest;
import org.deenwise.app.apis.repository.AssessmentRepository;
import org.deenwise.app.apis.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TeacherService {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AssessmentMapper assessmentMapper;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private LectureMapper lectureMapper;

    public AssessmentDTO createAssessment(AssessmentDTO assessmentDTO) {
        if(!Objects.isNull(assessmentDTO)) {
            AssessmentModel assessmentModel = assessmentMapper.convertToModel(assessmentDTO);
            assessmentRepository.save(assessmentModel);
            log.info("assessment details saved to database");
        }
        return assessmentDTO;
    }

    public LectureDTO createLecture(LectureDTO lectureDTO) {
        if(!Objects.isNull(lectureDTO)){
            LectureModel lectureModel = lectureMapper.convertToModel(lectureDTO);
            lectureRepository.save(lectureModel);
            log.info("lecture details saved to db.");
        }
        return lectureDTO;
    }

    public LectureDTO getLectureByID(Long id) {
        return lectureMapper.convertToDTO(lectureRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(String.format("Lecture Data with ID: %d Not Found",id))));
    }

    public AssessmentDTO getAssessmentById(Long id) {
        return assessmentMapper.convertToDTO(assessmentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(String.format("Assessment Data with ID: %d Not Found.",id))));
    }

    public Page<AssessmentDTO> getAssessments(AppPageRequest appPageRequest) {
        return assessmentRepository.findAll(appPageRequest.getPagination())
                .map(assessmentMapper::convertToDTO);
    }

    public Page<LectureDTO> getLectures(AppPageRequest appPageRequest) {
        return lectureRepository.findAll(appPageRequest.getPagination())
                .map(lectureMapper::convertToDTO);
    }

    public AssessmentDTO updateAssessmentByID(Long id, AssessmentDTO assessmentDTO) {
        AssessmentModel assessmentModel = assessmentRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(String.format("Assessment with id: %d Not found",id)));

        AssessmentModel newAssessmentModel = assessmentMapper.convertToModel(assessmentDTO);
        newAssessmentModel.setId(assessmentModel.getId());

        assessmentRepository.save(newAssessmentModel);
        log.info("assessment with id: {} details updated", id);
        return assessmentDTO;
    }

    public LectureDTO updateLectureByID(Long id, LectureDTO lectureDTO) {
        LectureModel lectureModel = lectureRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(String.format("Lecture with id: %d Not Found",id)));

        LectureModel newLectureModel = lectureMapper.convertToModel(lectureDTO);
        newLectureModel.setId(lectureDTO.getId());

        lectureRepository.save(newLectureModel);
        log.info("lecture with id: {} details updated",id);
        return lectureDTO;
    }

    public AssessmentDTO deleteAssessmentWithID(Long id) {
        AssessmentModel assessmentModel =
                assessmentRepository.findById(id)
                                .orElseThrow(() -> new NullPointerException(String.format("Assessment with id: %s Not Found",id)));
        if(!Objects.isNull(assessmentModel)) {
            assessmentRepository.deleteById(id);
            log.info("Assessment Data with id: {} removed from db", id);
        }
        return assessmentMapper.convertToDTO(assessmentModel);
    }

    public LectureDTO deleteLectureWithID(Long id) {
        LectureModel lectureModel =
                lectureRepository.findById(id)
                        .orElseThrow(() -> new NullPointerException(String.format("Lecture with id: %s Not Found",id)));
        if(!Objects.isNull(lectureModel)) {
            lectureRepository.deleteById(id);
            log.info("Assessment Data with id: {} removed from db", id);
        }
        return lectureMapper.convertToDTO(lectureModel);
    }

}
