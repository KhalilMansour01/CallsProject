package com.example.backend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.*;

@Entity
@Table(name = "calls")
public class CallsEntity {
    @Id
    @GeneratedValue
    @Column(name = "call_no")
    private double call_no;

    @Column(name = "c_code", nullable = false)
    private double c_code;

    @Column(name = "e_code")
    private double e_code;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = " req_time")
    private LocalTime req_time;

    @Column(name = "rp_code")
    private double rp_code;

    @Column(name = "resp_date")
    private LocalDate resp_date;

    @Column(name = "resp_time")
    private LocalTime resp_time;

    @Column(name = "time_arrive")
    private LocalTime time_arrive;

    @Column(name = "time_left")
    private LocalTime time_left;

    @Column(name = "susp_flag")
    private String susp_flag;

    @Column(name = "f_cat")
    private String f_cat;

    @Column(name = "inv_no", nullable = false)
    private int inv_no;

    @Column(name = "rem1")
    private String rem1;

    @Column(name = "rem2")
    private String rem2;

    @Column(name = "rem3")
    private String rem3;

    @Column(name = "rem4")
    private String rem4;

    @Column(name = "act_rem1")
    private String act_rem1;

    @Column(name = "act_rem2")
    private String act_rem2;

    @Column(name = "act_rem3")
    private String act_rem3;

    @Column(name = "act_rem4")
    private String act_rem4;

    public double getCall_no() {return call_no;}

    public void setCall_no(double call_no) {this.call_no = call_no;}

    public double getC_code() {return c_code;}

    public void setC_code(double c_code) {this.c_code = c_code;}

    public double getE_code() {return e_code;}

    public void setE_code(double e_code) {this.e_code = e_code;}

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    public LocalTime getReq_time() {return req_time;}

    public void setReq_time(LocalTime req_time) {this.req_time = req_time;}

    public double getRp_code() {return rp_code;}

    public void setRp_code(double rp_code) {this.rp_code = rp_code;}

    public LocalDate getResp_date() {return resp_date;}

    public void setResp_date(LocalDate resp_date) {this.resp_date = resp_date;}

    public LocalTime getResp_time() {return resp_time;}

    public void setResp_time(LocalTime resp_time) {this.resp_time = resp_time;}

    public LocalTime getTime_arrive() {return time_arrive;}

    public void setTime_arrive(LocalTime time_arrive) {this.time_arrive = time_arrive;}

    public LocalTime getTime_left() {return time_left;}

    public void setTime_left(LocalTime time_left) {this.time_left = time_left;}

    public String getSusp_flag() {return susp_flag;}

    public void setSusp_flag(String susp_flag) {this.susp_flag = susp_flag;}

    public String getF_cat() {return f_cat;}

    public void setF_cat(String f_cat) {this.f_cat = f_cat;}

    public int getInv_no() {return inv_no;}

    public void setInv_no(int inv_no) {this.inv_no = inv_no;}

    public String getRem1() {return rem1;}

    public void setRem1(String rem1) {this.rem1 = rem1;}

    public String getRem2() {return rem2;}

    public void setRem2(String rem2) {this.rem2 = rem2;}

    public String getRem3() {return rem3;}

    public void setRem3(String rem3) {this.rem3 = rem3;}

    public String getRem4() {return rem4;}

    public void setRem4(String rem4) {this.rem4 = rem4;}

    public String getAct_rem1() {return act_rem1;}

    public void setAct_rem1(String act_rem1) {this.act_rem1 = act_rem1;}

    public String getAct_rem2() {return act_rem2;}

    public void setAct_rem2(String act_rem2) {this.act_rem2 = act_rem2;}

    public String getAct_rem3() {return act_rem3;}

    public void setAct_rem3(String act_rem3) {this.act_rem3 = act_rem3;}

    public String getAct_rem4() {return act_rem4;}

    public void setAct_rem4(String act_rem4) {this.act_rem4 = act_rem4;}
}
