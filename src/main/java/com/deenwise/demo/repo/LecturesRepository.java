package com.deenwise.demo.repo;

import com.deenwise.demo.records.Lectures;
import com.deenwise.demo.response.LectureResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LecturesRepository extends JpaRepository<Lectures, Long> {

}
