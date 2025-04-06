package com.deenwise.demo.records;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Lectures {

    @Id
    private Long id;
    private String courseName;
    @Lob
    private byte[] recorded;
}
