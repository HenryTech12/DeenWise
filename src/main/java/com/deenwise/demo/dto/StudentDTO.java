package com.deenwise.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentDTO
{
	private String number;

    private String email;
    private String studentId;
    private String name;
    private List<String> courses;
    private String desc;
    private String joined;
    private String plan;
}

