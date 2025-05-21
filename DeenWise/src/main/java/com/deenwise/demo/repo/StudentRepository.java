package com.deenwise.demo.repo;

import com.deenwise.demo.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel,Long>
{
    Optional<StudentModel> findByEmail(String email);
}
