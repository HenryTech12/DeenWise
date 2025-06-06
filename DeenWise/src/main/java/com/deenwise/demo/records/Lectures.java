package com.deenwise.demo.records;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Lectures {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String title;
    private String desc;
    @Lob
    private byte[] video;
    private String courseName;
    private Integer lessonOrder;
    private LocalDate uploadedDate;
}
