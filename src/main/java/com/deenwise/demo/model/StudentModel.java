package com.deenwise.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StudentModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String studentId;
    private String name;
    private List<String> courses;
    @Column(name = "description")
    private String desc;
    private String joined;
    private String plan;
}
