package com.deenwise.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PaymentModel {

    @Id
    private Long id;
    private String plan;
    private Double amount;
    private boolean access;
}
