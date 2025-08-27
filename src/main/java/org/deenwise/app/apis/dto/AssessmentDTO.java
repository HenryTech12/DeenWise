package org.deenwise.app.apis.dto;

import lombok.Data;

@Data
public class AssessmentDTO {

    private Long id;
    private String assessmentType;
    private String title;
    private String description;
    private String duedate;
    private String duetime;
    private byte[] todo;
}
