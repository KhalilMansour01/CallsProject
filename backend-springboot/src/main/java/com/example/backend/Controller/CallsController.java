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

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Service.CallsService;
import com.example.backend.Exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/calls")
public class CallsController {
    @Autowired
    CallsService callsService;

    @GetMapping("/all")
    public ResponseEntity<List<CallsEntity>> getAllCalls() {
        List<CallsEntity> list = callsService.getAllCalls();
        return new ResponseEntity<List<CallsEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallsEntity> getCallsById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return callsService.getCallsById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CallsEntity> createCalls(@RequestBody CallsEntity calls) throws ResourceNotFoundException {
        return callsService.createCalls(calls);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CallsEntity> updateCalls(@PathVariable("id") BigDecimal id, @RequestBody CallsEntity calls)
            throws ResourceNotFoundException {
        return callsService.updateCalls(id, calls);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CallsEntity> deleteCallsById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return callsService.deleteCallsById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CallsEntity>> filterCalls(
            @RequestParam(required = false) String fCat,
            @RequestParam(required = false) boolean open) {

        List<CallsEntity> calls = callsService.filterCalls(fCat, open);
        return ResponseEntity.ok().body(calls);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CallsEntity>> searchCalls(
            @RequestParam(required = false) String searchQuery) {

        List<CallsEntity> calls = callsService.searchCalls(searchQuery);
        return ResponseEntity.ok().body(calls);
    }
}