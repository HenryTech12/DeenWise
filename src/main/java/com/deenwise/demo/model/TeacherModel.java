package com.deenwise.demo.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeacherModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String joined;
    private String number;
}
