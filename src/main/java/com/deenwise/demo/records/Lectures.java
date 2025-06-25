package com.deenwise.demo.records;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Lectures {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "description")
    private String desc;
    private String courseName;
    private Integer lessonOrder;
    private LocalDate uploadedDate;
    private String status;
    private String assignedBy;
}
