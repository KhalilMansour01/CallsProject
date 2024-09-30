package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Specifications.CustomerSpecifications;
import com.example.backend.Exceptions.DuplicateIdException;
import com.example.backend.Exceptions.MissingValueException;
import com.example.backend.Exceptions.ResourceNotFoundException;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    // GET ALL CUSTOMER
    public List<CustomerEntity> getAllCustomer() {
        List<CustomerEntity> customerList = customerRepository.findAll();

        if (customerList.size() > 0) {
            return customerList;
        } else {
            return new ArrayList<CustomerEntity>();
        }
    }

    // GET CUSTOMER BY ID
    public ResponseEntity<CustomerEntity> getCustomerById(BigDecimal id) throws ResourceNotFoundException {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given id :: " + id));
        return ResponseEntity.ok().body(customer);
    }

    // CREATE CUSTOMER
    public ResponseEntity<CustomerEntity> createCustomer(CustomerEntity entity)
            throws DuplicateIdException {

        StringBuilder errorMessages = new StringBuilder("Required Fields:\n");

        boolean missingFields = false;

        if (entity.getId() == null) {
            errorMessages.append("- ID\n");
            missingFields = true;
        }
        if (entity.getCustName() == null) {
            errorMessages.append("- Customer Name\n");
            missingFields = true;
        }
        if (entity.getCvlCode() == null) {
            errorMessages.append("- Civil Code\n");
            missingFields = true;
        }
        if (entity.getRegCode() == null) {
            errorMessages.append("- Region Code\n");
            missingFields = true;
        }
        if (entity.getCntrCode() == null) {
            errorMessages.append("- Country Code\n");
            missingFields = true;
        }

        if (missingFields) {
            throw new MissingValueException(errorMessages.toString().trim());
        }

        boolean exists = customerRepository.existsById(entity.getId());

        if (exists) {
            throw new DuplicateIdException("Customer record exists for given ID : " + entity.getId());
        } else {

            CustomerEntity savedEntity = customerRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        }
    }

    // UPDATE CUSTOMER
    public ResponseEntity<CustomerEntity> updateCustomer(BigDecimal id, CustomerEntity entity)
            throws ResourceNotFoundException {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given ID : " + id));

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

        final CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // DELETE CUSTOMER BY ID
    public ResponseEntity<CustomerEntity> deleteCustomerById(BigDecimal id) throws ResourceNotFoundException {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customer record exist for given id :: " + id));

        customerRepository.delete(customer);
        return ResponseEntity.ok().build();
    }

    // FILTER CUSTOMERS BY REGION AND COUNTRY
    public List<CustomerEntity> filterCustomers(String regCode, String cntrCode) {
        Specification<CustomerEntity> spec = Specification.where(CustomerSpecifications.hasRegion(regCode))
                .and(CustomerSpecifications.hasCountry(cntrCode));
        return customerRepository.findAll(spec);
    }

    // SEARCH CUSTOMERS BY QUERY
    public List<CustomerEntity> searchCustomers(String searchQuery) {
        Specification<CustomerEntity> spec = CustomerSpecifications.searchByMultipleFields(searchQuery);
        return customerRepository.findAll(spec);
    }

    // FILTER AND SEARCH CUSTOMERS
    public List<CustomerEntity> filterAndSearchCustomers(String regCode, String cntrCode, String searchQuery) {
        Specification<CustomerEntity> spec = Specification
                .where(CustomerSpecifications.hasRegion(regCode))
                .and(CustomerSpecifications.hasCountry(cntrCode))
                .and(CustomerSpecifications.searchByMultipleFields(searchQuery));

        return customerRepository.findAll(spec);
    }

}
