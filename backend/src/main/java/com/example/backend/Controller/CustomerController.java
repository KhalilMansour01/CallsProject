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


import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Service.CustomerService;
import com.example.backend.Exceptions.RecordNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    CustomerService service;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerEntity>> getAllCustomer() {

        List<CustomerEntity> list = service.getAllCustomer();
        return new ResponseEntity<List<CustomerEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        CustomerEntity entity = service.getCustomerById(id);
        return new ResponseEntity<CustomerEntity>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer) throws RecordNotFoundException {
        service.createCustomer(customer);
        return new ResponseEntity<CustomerEntity>(customer, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable("id") BigDecimal id, @RequestBody CustomerEntity customer) throws RecordNotFoundException {
        service.updateCustomer(id, customer);
        return new ResponseEntity<CustomerEntity>(service.getCustomerById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable("id") BigDecimal id) throws RecordNotFoundException {
        service.deleteCustomerById(id);
    }
    
}
