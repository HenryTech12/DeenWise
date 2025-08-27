package org.deenwise.app.apis.dto;

import lombok.Data;

@Data
public class LectureDTO {

    private Long id;
    private String title;
    private String description;
    private String assignedCourse;
    private String lecture_video;


}
