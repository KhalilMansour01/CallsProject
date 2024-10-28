package com.example.backend.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @Column(name = "cust_no")
    private BigDecimal id;

    @Column(name = "cvl_code")
    private BigDecimal cvlCode;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_name_a")
    private String custNameA;

    @Column(name = "tel1")
    private String tel1;

    @Column(name = "tel2")
    private String tel2;

    @Column(name = "tel3")
    private String tel3;

    @Column(name = "fax")
    private String fax;

    @Column(name = "reg_code")
    private String regCode;

    @Column(name = "cntr_code")
    private String cntrCode;

    @Column(name = "contact")
    private String contact;

    @Column(name = "contact_a")
    private String contactA;

    @Column(name = "vat_status")
    private String vatStatus;

    @Column(name = "vat_cash")
    private String vatCash;

    @Column(name = "email")
    private String email;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "status")
    private boolean status = true;

    public BigDecimal getId() {
        return id;
    }
    
    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getCvlCode() {
        return cvlCode;
    }
    
    public void setCvlCode(BigDecimal cvlCode) {
        this.cvlCode = cvlCode;
    }
    
    public String getCustName() {
        return custName;
    }
    
    public void setCustName(String custName) {
        this.custName = custName;
    }
    
    public String getCustNameA() {
        return custNameA;
    }
    
    public void setCustNameA(String custNameA) {
        this.custNameA = custNameA;
    }
    
    public String getTel1() {
        return tel1;
    }
    
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }
    
    public String getTel2() {
        return tel2;
    }
    
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }
    
    public String getTel3() {
        return tel3;
    }
    
    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getRegCode() {
        return regCode;
    }
    
    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }
    
    public String getCntrCode() {
        return cntrCode;
    }
    
    public void setCntrCode(String cntrCode) {
        this.cntrCode = cntrCode;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getContactA() {
        return contactA;
    }
    
    public void setContactA(String contactA) {
        this.contactA = contactA;
    }
    
    public String getVatStatus() {
        return vatStatus;
    }
    
    public void setVatStatus(String vatStatus) {
        this.vatStatus = vatStatus;
    }
    
    public String getVatCash() {
        return vatCash;
    }
    
    public void setVatCash(String vatCash) {
        this.vatCash = vatCash;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public boolean getStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", cvlCode=" + cvlCode +
                ", custName='" + custName + '\'' +
                ", custNameA='" + custNameA + '\'' +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", tel3='" + tel3 + '\'' +
                ", fax='" + fax + '\'' +
                ", regCode='" + regCode + '\'' +
                ", cntrCode='" + cntrCode + '\'' +
                ", contact='" + contact + '\'' +
                ", contactA='" + contactA + '\'' +
                ", vatStatus='" + vatStatus + '\'' +
                ", vatCash='" + vatCash + '\'' +
                ", email='" + email + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                '}';
    }
}
