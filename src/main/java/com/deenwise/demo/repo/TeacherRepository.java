package com.deenwise.demo.repo;

import com.deenwise.demo.model.TeacherModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherModel, Long>
{
    @Cacheable("teacherEmail")
    Optional<TeacherModel> findByEmail(String email);
}
