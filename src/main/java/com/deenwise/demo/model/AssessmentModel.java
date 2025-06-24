package com.deenwise.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class AssessmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String title;
    @Column(name = "description")
    private String desc;
    private String duedate;
    private String duetime;
    private String created;
    private String status;
    private String submittedOn;
    private String gradedOn;
}
