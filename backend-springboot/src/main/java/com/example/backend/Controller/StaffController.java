package com.example.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.Entity.StaffEntity;
import com.example.backend.Service.StaffService;
import com.example.backend.Exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    StaffService staffService;

    @GetMapping("/all")
    public ResponseEntity<List<StaffEntity>> getAllStaff() {
        List<StaffEntity> list = staffService.getAllStaff();
        return new ResponseEntity<List<StaffEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffEntity> getStaffById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return staffService.getStaffById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<StaffEntity> createStaff(@RequestBody StaffEntity staff) throws ResourceNotFoundException {
        return staffService.createStaff(staff);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StaffEntity> updateStaff(@PathVariable("id") BigDecimal id, @RequestBody StaffEntity staff)
            throws ResourceNotFoundException {
        return staffService.updateStaff(id, staff);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StaffEntity> deleteStaffById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return staffService.deleteStaffById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<StaffEntity>> filterStaff(
        @RequestParam(required = false) String dptCode) {
        
        List<StaffEntity> staff = staffService.filterStaff(dptCode);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StaffEntity>> searchStaff(
            @RequestParam(required = false) String searchQuery) {
        try {
            List<StaffEntity> staff = staffService.searchStaff(searchQuery);
            return ResponseEntity.ok(staff);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}