package com.deenwise.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StudentModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String number;
    private String email;
    private String studentId;
    private String name;
    private List<String> courses;
    private String desc;
    private String joined;
    private String plan;
}
