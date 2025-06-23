package com.deenwise.demo.repo;

import com.deenwise.demo.records.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturesRepository extends JpaRepository<Lectures, Long> {
}
