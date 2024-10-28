package com.example.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Service.CustomerService;
import com.example.backend.Exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerEntity>> getAllCustomer() {
        List<CustomerEntity> list = customerService.getAllCustomer();
        return new ResponseEntity<List<CustomerEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customer)
            throws ResourceNotFoundException {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable("id") BigDecimal id,
            @RequestBody CustomerEntity customer) throws ResourceNotFoundException {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomerEntity> deleteCustomerById(@PathVariable("id") BigDecimal id)
            throws ResourceNotFoundException {
        return customerService.deleteCustomerById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerEntity>> filterCustomer(
            @RequestParam(required = false) String regCode,
            @RequestParam(required = false) String cntrCode) {

        List<CustomerEntity> customers = customerService.filterCustomers(regCode, cntrCode);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerEntity>> searchCustomer(
            @RequestParam(required = false) String searchQuery) {

        List<CustomerEntity> customers = customerService.searchCustomers(searchQuery);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/filterAndSearch")
    public ResponseEntity<List<CustomerEntity>> searchAndFilterCustomer(
            @RequestParam(required = false) String regCode,
            @RequestParam(required = false) String cntrCode,
            @RequestParam(required = false) String searchQuery) {

        List<CustomerEntity> customers = customerService.filterAndSearchCustomers(regCode, cntrCode, searchQuery);
        return ResponseEntity.ok(customers);
    }

    /*
     * Advanced GET request to fetch paginated customers with sorting and filtering
     */
    @GetMapping("/advancedGet")
    public ResponseEntity<Page<CustomerEntity>> pagedCustomers(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String regCode,
            @RequestParam(required = false) String cntrCode,
            @RequestParam(required = false) String searchQuery) {

        Sort sort = Sort.by("asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortField);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<CustomerEntity> customersPage = customerService.paginatedCustomers(pageable, regCode, cntrCode,
                searchQuery);

        if (customersPage.hasContent()) {
            return ResponseEntity.ok(customersPage);
        } else {
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(), pageable, 0));
            // return ResponseEntity.noContent().build();
        }
    }

    /*
     * Search customers by name
     */
    @GetMapping("/searchByName")
    public ResponseEntity<List<CustomerEntity>> searchByName(
            @RequestParam(required = false) String searchQuery) {
        List<CustomerEntity> customers = customerService.searchByName(searchQuery);
        return ResponseEntity.ok(customers);
    }

}