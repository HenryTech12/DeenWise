package com.deenwise.demo.repo;

import com.deenwise.demo.model.AssessmentModel;
import com.deenwise.demo.model.TeacherModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssessmentRepository extends JpaRepository<AssessmentModel,Long> {

        @Cacheable("aDetails1")
        Optional<List<AssessmentModel>> findByDuedateGreaterThan(String duedate);
        @Cacheable("aDetails2")
        Optional<List<AssessmentModel>> findByTypeAndDuedateGreaterThan(String type, String duedate);
}
