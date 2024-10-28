package com.example.backend.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "staff")
public class StaffEntity {

    @Id
    @Column(name = "staff_no")
    private BigDecimal id;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "m_name")
    private String middleName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "cvl_code")
    private BigDecimal cvlCode;

    @Column(name = "dpt_code")
    private String dptCode;

    @Column(name = "status")
    private boolean status = true;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCvlCode() {
        return cvlCode;
    }

    public void setCvlCode(BigDecimal cvlCode) {
        this.cvlCode = cvlCode;
    }

    public String getDptCode() {
        return dptCode;
    }

    public void setDptCode(String dptCode) {
        this.dptCode = dptCode;
    }

    public boolean getStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StaffEntity{" +
                "staff_no=" + id +
                ", f_name='" + firstName + '\'' +
                ", m_name='" + middleName + '\'' +
                ", l_name='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", cvl_code=" + cvlCode +
                ", dpt_code='" + dptCode + '\'' +
                '}';
    }
}
