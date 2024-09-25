package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Specifications.CustomerSpecifications;
import com.example.backend.Exceptions.ResourceNotFoundException;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    // GET ALL CUSTOMER
    public List<CustomerEntity> getAllCustomer() {
        List<CustomerEntity> customerList = repository.findAll();

        if (customerList.size() > 0) {
            return customerList;
        } else {
            return new ArrayList<CustomerEntity>();
        }
    }

    // GET CUSTOMER BY ID
    public ResponseEntity<CustomerEntity> getCustomerById(BigDecimal id) throws ResourceNotFoundException {
        CustomerEntity customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given id :: " + id));
        return ResponseEntity.ok().body(customer);
    }

    // CREATE CUSTOMER
    public ResponseEntity<CustomerEntity> createCustomer(CustomerEntity entity) throws ResourceNotFoundException {
        boolean exists = repository.existsById(entity.getId());
        if (exists) {
            throw new ResourceNotFoundException("Customer record exists for given id :: " + entity.getId());
        } else {
            repository.save(entity);
            return ResponseEntity.status(201).body(entity);
        }
    }

    // UPDATE CUSTOMER
    public ResponseEntity<CustomerEntity> updateCustomer(BigDecimal id, CustomerEntity entity)
            throws ResourceNotFoundException {
        CustomerEntity existingCustomer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given id :: " + id));

        if (entity.getCvlCode() != null) {
            existingCustomer.setCvlCode(entity.getCvlCode());
        }
        if (entity.getCustName() != null) {
            existingCustomer.setCustName(entity.getCustName());
        }
        if (entity.getCustNameA() != null) {
            existingCustomer.setCustNameA(entity.getCustNameA());
        }
        if (entity.getTel1() != null) {
            existingCustomer.setTel1(entity.getTel1());
        }
        if (entity.getTel2() != null) {
            existingCustomer.setTel2(entity.getTel2());
        }
        if (entity.getTel3() != null) {
            existingCustomer.setTel3(entity.getTel3());
        }
        if (entity.getFax() != null) {
            existingCustomer.setFax(entity.getFax());
        }
        if (entity.getRegCode() != null) {
            existingCustomer.setRegCode(entity.getRegCode());
        }
        if (entity.getCntrCode() != null) {
            existingCustomer.setCntrCode(entity.getCntrCode());
        }
        if (entity.getContact() != null) {
            existingCustomer.setContact(entity.getContact());
        }
        if (entity.getContactA() != null) {
            existingCustomer.setContactA(entity.getContactA());
        }
        if (entity.getVatStatus() != null) {
            existingCustomer.setVatStatus(entity.getVatStatus());
        }
        if (entity.getVatCash() != null) {
            existingCustomer.setVatCash(entity.getVatCash());
        }
        if (entity.getEmail() != null) {
            existingCustomer.setEmail(entity.getEmail());
        }
        if (entity.getAddress1() != null) {
            existingCustomer.setAddress1(entity.getAddress1());
        }
        if (entity.getAddress2() != null) {
            existingCustomer.setAddress2(entity.getAddress2());
        }

        final CustomerEntity updatedCustomer = repository.save(existingCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // DELETE CUSTOMER BY ID
    public ResponseEntity<CustomerEntity> deleteCustomerById(BigDecimal id) throws ResourceNotFoundException {
        CustomerEntity customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given id :: " + id));

        repository.delete(customer);
        return ResponseEntity.ok().build();
    }

    // FILTER CUSTOMERS BY REGION AND COUNTRY
    public List<CustomerEntity> filterCustomers(String regCode, String cntrCode) {
        Specification<CustomerEntity> spec = Specification.where(CustomerSpecifications.hasRegion(regCode))
                .and(CustomerSpecifications.hasCountry(cntrCode));
        return repository.findAll(spec);
    }

    // SEARCH CUSTOMERS BY QUERY
    public List<CustomerEntity> searchCustomers(String searchQuery) {
        Specification<CustomerEntity> spec = CustomerSpecifications.searchByMultipleFields(searchQuery);
        return repository.findAll(spec);
    }

    // FILTER AND SEARCH CUSTOMERS
    public List<CustomerEntity> filterAndSearchCustomers(String regCode, String cntrCode, String searchQuery) {
        Specification<CustomerEntity> spec = Specification
                .where(CustomerSpecifications.hasRegion(regCode))
                .and(CustomerSpecifications.hasCountry(cntrCode))
                .and(CustomerSpecifications.searchByMultipleFields(searchQuery));

        return repository.findAll(spec);
    }

}
