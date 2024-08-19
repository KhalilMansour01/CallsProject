package com.example.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.backend.Entity.StaffEntity;
import com.example.backend.Service.StaffService;

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

   
}
