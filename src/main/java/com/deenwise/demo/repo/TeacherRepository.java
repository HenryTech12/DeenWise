package com.deenwise.demo.repo;

import com.deenwise.demo.model.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherModel, Long>
{
}
