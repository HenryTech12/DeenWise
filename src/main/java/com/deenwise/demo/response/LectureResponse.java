package com.deenwise.demo.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LectureResponse {
    private String title;
    private String desc;
    private String courseName;
    private Integer lessonOrder;
    private LocalDate uploadedDate;
    private String status;
    private String assignedBy;
}
