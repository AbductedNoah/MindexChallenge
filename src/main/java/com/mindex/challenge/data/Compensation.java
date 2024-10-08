package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Compensation {

    @DBRef
    private Employee employee;

    private BigDecimal salary;
    private LocalDate effectiveDate;

    // Constructors, getters, and setters

    public Compensation() {}

    public Compensation(Employee employee, BigDecimal salary, LocalDate effectiveDate) {
    	this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
