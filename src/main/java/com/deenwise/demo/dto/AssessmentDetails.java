package com.deenwise.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AssessmentDetails {

    private String type;
    private String title;
    private String desc;
    private String duedate;
    private String duetime;
    private String created;
    private String status;
    private String submittedOn;
    private String gradedOn;
}
