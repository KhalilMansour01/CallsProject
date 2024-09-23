package com.example.backend.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "calls")
public class CallsEntity {
    @Id
    @Column(name = "call_no")
    private BigDecimal id;

    
    @Column(name = "c_code", nullable = false)
    @NotNull(message = "Client Id required")
    private BigDecimal cCode;

    @Column(name = "e_code", nullable = false)
    private BigDecimal eCode;

    @Column(name = "req_date", nullable = false)
    private Date reqDate;

    @Column(name = " req_time", nullable = false)
    private LocalTime reqTime;

    @Column(name = "rp_code")
    private BigDecimal rpCode;

    @Column(name = "resp_date")
    private Date respDate;

    @Column(name = "resp_time")
    private LocalTime respTime;

    @Column(name = "time_arrive")
    private LocalTime timeArrive;

    @Column(name = "time_left")
    private LocalTime timeLeft;

    @Column(name = "susp_flag")
    private String suspFlag;

    @Column(name = "f_cat")
    private String fCat;

    @Column(name = "inv_no")
    private Integer invNo;

    @Column(name = "rem1")
    private String rem1;

    @Column(name = "rem2")
    private String rem2;

    @Column(name = "rem3")
    private String rem3;

    @Column(name = "rem4")
    private String rem4;

    @Column(name = "act_rem1")
    private String actRem1;

    @Column(name = "act_rem2")
    private String actRem2;

    @Column(name = "act_rem3")
    private String actRem3;

    @Column(name = "act_rem4")
    private String actRem4;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getCCode() {
        return cCode;
    }

    public void setCCode(BigDecimal cCode) {
        this.cCode = cCode;
    }

    public BigDecimal getECode() {
        return eCode;
    }

    public void setECode(BigDecimal eCode) {
        this.eCode = eCode;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public LocalTime getReqTime() {
        return reqTime;
    }

    public void setReqTime(LocalTime reqTime) {
        this.reqTime = reqTime;
    }

    public BigDecimal getRpCode() {
        return rpCode;
    }

    public void setRpCode(BigDecimal rpCode) {
        this.rpCode = rpCode;
    }

    public Date getRespDate() {
        return respDate;
    }

    public void setRespDate(Date respDate) {
        this.respDate = respDate;
    }

    public LocalTime getRespTime() {
        return respTime;
    }

    public void setRespTime(LocalTime respTime) {
        this.respTime = respTime;
    }

    public LocalTime getTimeArrive() {
        return timeArrive;
    }

    public void setTimeArrive(LocalTime timeArrive) {
        this.timeArrive = timeArrive;
    }

    public LocalTime getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(LocalTime timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getSuspFlag() {
        return suspFlag;
    }

    public void setSuspFlag(String suspFlag) {
        this.suspFlag = suspFlag;
    }

    public String getFCat() {
        return fCat;
    }

    public void setFCat(String fCat) {
        this.fCat = fCat;
    }

    public Integer getInvNo() {
        return invNo;
    }

    public void setInvNo(Integer invNo) {
        this.invNo = invNo;
    }

    public String getRem1() {
        return rem1;
    }

    public void setRem1(String rem1) {
        this.rem1 = rem1;
    }

    public String getRem2() {
        return rem2;
    }

    public void setRem2(String rem2) {
        this.rem2 = rem2;
    }

    public String getRem3() {
        return rem3;
    }

    public void setRem3(String rem3) {
        this.rem3 = rem3;
    }

    public String getRem4() {
        return rem4;
    }

    public void setRem4(String rem4) {
        this.rem4 = rem4;
    }

    public String getActRem1() {
        return actRem1;
    }

    public void setActRem1(String actRem1) {
        this.actRem1 = actRem1;
    }

    public String getActRem2() {
        return actRem2;
    }

    public void setActRem2(String actRem2) {
        this.actRem2 = actRem2;
    }

    public String getActRem3() {
        return actRem3;
    }

    public void setActRem3(String actRem3) {
        this.actRem3 = actRem3;
    }

    public String getActRem4() {
        return actRem4;
    }

    public void setActRem4(String actRem4) {
        this.actRem4 = actRem4;
    }

    @Override
    public String toString() {
        return "CallsEntity{" +
                "id=" + id +
                ", cCode=" + cCode +
                ", eCode=" + eCode +
                ", reqDate=" + reqDate +
                ", reqTime=" + reqTime +
                ", rpCode=" + rpCode +
                ", respDate=" + respDate +
                ", respTime=" + respTime +
                ", timeArrive=" + timeArrive +
                ", timeLeft=" + timeLeft +
                ", suspFlag='" + suspFlag + '\'' +
                ", fCat='" + fCat + '\'' +
                ", invNo=" + invNo +
                ", rem1='" + rem1 + '\'' +
                ", rem2='" + rem2 + '\'' +
                ", rem3='" + rem3 + '\'' +
                ", rem4='" + rem4 + '\'' +
                ", actRem1='" + actRem1 + '\'' +
                ", actRem2='" + actRem2 + '\'' +
                ", actRem3='" + actRem3 + '\'' +
                ", actRem4='" + actRem4 + '\'' +
                '}';
    }

    public boolean isOpen() {
        return this.timeLeft == null && this.timeArrive == null;
    }
}
