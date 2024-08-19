package com.example.backend.Entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class StaffEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "staff_no")
    private BigDecimal staff_no;

    @Column(name = "f_name")
    private String f_name;

    @Column(name = "m_name")
    private String m_name;

    @Column(name = "l_name")
    private String l_name;

    @Column(name = "title")
    private String title;

    @Column(name = "cvl_code")
    private double cvl_code;

    @Column(name = "dpt_code")
    private String dpt_code;

    public BigDecimal getId() {return staff_no;}

    public void setId(BigDecimal staff_no) {this.staff_no = staff_no;}

    public String getF_name() {return f_name;}

    public void setF_name(String f_name) {this.f_name = f_name;}

    public String getM_name() {return m_name;}

    public void setM_name(String m_name) {this.m_name = m_name;}

    public String getL_name() {return l_name;}

    public void setL_name(String l_name) {this.l_name = l_name;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public double getCvl_code() {return cvl_code;}

    public void setCvl_code(double cvl_code) {this.cvl_code = cvl_code;}

    public String getDpt_code() {return dpt_code;}

    public void setDpt_code(String dpt_code) {this.dpt_code = dpt_code;}
}


