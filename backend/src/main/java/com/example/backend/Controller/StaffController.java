package com.example.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import com.example.backend.Entity.StaffEntity;
import com.example.backend.Service.StaffService;
import com.example.backend.Exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    StaffService service;

    @GetMapping("/all")
    public ResponseEntity<List<StaffEntity>> getAllStaff() {

        List<StaffEntity> list = service.getAllStaff();
        return new ResponseEntity<List<StaffEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffEntity> getStaffById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        StaffEntity entity = service.getStaffById(id);
        return new ResponseEntity<StaffEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<StaffEntity> createStaff(@RequestBody StaffEntity staff) throws RecordNotFoundException {
        service.createStaff(staff);
        return new ResponseEntity<StaffEntity>(staff, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StaffEntity> updateStaff(@PathVariable("id") BigDecimal id, @RequestBody StaffEntity staff) throws RecordNotFoundException {
        service.updateStaff(id, staff);
        return new ResponseEntity<StaffEntity>(service.getStaffById(id), new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteStaffById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        service.deleteStaffById(id);
    }

}
