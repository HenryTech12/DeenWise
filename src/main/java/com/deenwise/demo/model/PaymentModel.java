package com.deenwise.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentModel {

    @Id
    private Long id;
    private String plan;
    private Double amount;
    private boolean access;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }
}
