package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Exceptions.RecordNotFoundException;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository repository;

    public List<CustomerEntity> getAllCustomer() {
        List<CustomerEntity> customerList = repository.findAll();

        if (customerList.size() > 0) {
            return customerList;
        } else {
            return new ArrayList<CustomerEntity>();
        }
    }

    public CustomerEntity getCustomerById(BigDecimal id) throws RecordNotFoundException {
        Optional<CustomerEntity> customer = repository.findById(id);

        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new RecordNotFoundException("No customer record exist for given id");
        }
    }

    public CustomerEntity createCustomer(CustomerEntity entity) throws RecordNotFoundException {
        Optional<CustomerEntity> customer = repository.findById(entity.getId());
        if(customer.isPresent()){
            throw new RecordNotFoundException("Customer record exist for given id");
        } else {
            return repository.save(entity);
        }
    }

    public CustomerEntity updateCustomer(BigDecimal id, CustomerEntity entity) throws RecordNotFoundException {
        CustomerEntity existingCustomer = repository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("No customer record exist for given id"));

        if (entity.getCvl_code() != null) {
            existingCustomer.setCvl_code(entity.getCvl_code());
        }
        if (entity.getCust_name() != null) {
            existingCustomer.setCust_name(entity.getCust_name());
        }
        if (entity.getCust_name_a() != null) {
            existingCustomer.setCust_name_a(entity.getCust_name_a());
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
        if (entity.getReg_code() != null) {
            existingCustomer.setReg_code(entity.getReg_code());
        }
        if (entity.getCntr_code() != null) {
            existingCustomer.setCntr_code(entity.getCntr_code());
        }
        if (entity.getContact() != null) {
            existingCustomer.setContact(entity.getContact());
        }
        if (entity.getContact_a() != null) {
            existingCustomer.setContact_a(entity.getContact_a());
        }
        if (entity.getVat_status() != null) {
            existingCustomer.setVat_status(entity.getVat_status());
        }
        if (entity.getVat_cash() != null) {
            existingCustomer.setVat_cash(entity.getVat_cash());
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

        return repository.save(existingCustomer);
    }

    public void deleteCustomerById(BigDecimal id) throws RecordNotFoundException {
        Optional<CustomerEntity> customer = repository.findById(id);

        if (customer.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No customer record exist for given id");
        }
    }
}

