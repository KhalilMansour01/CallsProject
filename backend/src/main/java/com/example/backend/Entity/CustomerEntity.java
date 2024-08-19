package com.example.backend.Entity;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @Column(name = "cust_no")
    private BigDecimal cust_no;

    @Column(name = "cvl_code")
    private BigDecimal cvl_code;

    @Column(name = "cust_name")
    private String cust_name;

    @Column(name = "cust_name_a")
    private String cust_name_a;

    @Column(name = "tel1")
    private String tel1;

    @Column(name = "tel2")
    private String tel2;

    @Column(name = "tel3")
    private String tel3;

    @Column(name = "fax")
    private String fax;

    @Column(name = "reg_code")
    private String reg_code;

    @Column(name = "cntr_code")
    private String cntr_code;

    @Column(name = "contact")
    private String contact;

    @Column(name = "contact_a")
    private String contact_a;

    @Column(name = "vat_status")
    private String vat_status;

    @Column(name = "vat_cash")
    private String vat_cash;

    @Column(name = "email")
    private String email;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    public BigDecimal getId() {
        return cust_no;
    }

    public void setId(BigDecimal cust_no) {
        this.cust_no = cust_no;
    }

    public BigDecimal getCvl_code() {
        return cvl_code;
    }
    
    public void setCvl_code(BigDecimal cvl_code) {
        this.cvl_code = cvl_code;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_name_a() {
        return cust_name_a;
    }

    public void setCust_name_a(String cust_name_a) {
        this.cust_name_a = cust_name_a;
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

    public String getReg_code() {
        return reg_code;
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public String getCntr_code() {
        return cntr_code;
    }

    public void setCntr_code(String cntr_code) {
        this.cntr_code = cntr_code;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact_a() {
        return contact_a;
    }

    public void setContact_a(String contact_a) {
        this.contact_a = contact_a;
    }

    public String getVat_status() {
        return vat_status;
    }

    public void setVat_status(String vat_status) {
        this.vat_status = vat_status;
    }

    public String getVat_cash() {
        return vat_cash;
    }

    public void setVat_cash(String vat_cash) {
        this.vat_cash = vat_cash;
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
}
