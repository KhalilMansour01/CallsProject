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

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Service.CallsService;
import com.example.backend.Exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/calls")
public class CallsController {
    
    @Autowired
    CallsService service;

    @GetMapping("/all")
    public ResponseEntity<List<CallsEntity>> getAllCalls() {

        List<CallsEntity> list = service.getAllCalls();
        return new ResponseEntity<List<CallsEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallsEntity> getCallsById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        CallsEntity entity = service.getCallsById(id);
        return new ResponseEntity<CallsEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CallsEntity> createCalls(@RequestBody CallsEntity calls) throws RecordNotFoundException {
        service.createCalls(calls);
        return new ResponseEntity<CallsEntity>(calls, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CallsEntity> updateCalls(@PathVariable("id") BigDecimal id, @RequestBody CallsEntity calls) throws RecordNotFoundException {
        service.updateCalls(id, calls);
        return new ResponseEntity<CallsEntity>(service.getCallsById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCallsById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        service.deleteCallsById(id);
    }
}
