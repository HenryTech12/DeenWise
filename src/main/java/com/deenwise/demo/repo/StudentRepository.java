package com.deenwise.demo.repo;

import com.deenwise.demo.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentModel,Long>
{
}
