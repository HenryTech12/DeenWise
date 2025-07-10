package com.deenwise.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class LectureDTO {

    private String title;
    private String desc;
    private LocalDate uploadedDate;
    private String courseName;
    private Integer lessonOrder;
    private String status;
    private String assignedBy;
}
