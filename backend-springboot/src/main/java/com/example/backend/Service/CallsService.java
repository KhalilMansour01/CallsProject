package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Entity.CustomerEntity;
import com.example.backend.Entity.StaffEntity;
import com.example.backend.Exceptions.DuplicateIdException;
import com.example.backend.Exceptions.MissingValueException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.Repository.CallsRepository;
import com.example.backend.Specifications.CallsSpecification;

@Service
public class CallsService {
    @Autowired
    CallsRepository callsRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    StaffService staffService;

    // GET ALL CALLS
    public List<CallsEntity> getAllCalls() {
        List<CallsEntity> result = (List<CallsEntity>) callsRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<CallsEntity>();
        }
    }

    // GET CALLS BY ID
    public ResponseEntity<CallsEntity> getCallsById(BigDecimal id) throws ResourceNotFoundException {
        CallsEntity calls = callsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No calls record exist for given id : " + id));

        return ResponseEntity.ok().body(calls);
    }

    // CREATE CALLS
    public ResponseEntity<CallsEntity> createCalls(CallsEntity callsEntity)
            throws DuplicateIdException {

        StringBuilder errorMessages = new StringBuilder("Required Fields:\n");

        boolean missingFields = false;

        if (callsEntity.getId() == null) {
            errorMessages.append("- ID\n");
            missingFields = true;
        } else {
            String idString = callsEntity.getId().toPlainString();
            if (idString.length() > 10) {
                errorMessages.append("- ID must be 10 digits or fewer\n");
                missingFields = true;
            }
            if (callsEntity.getId().compareTo(BigDecimal.ZERO) <= 0) {
                errorMessages.append("- ID must be greater than zero\n");
                missingFields = true;
            }
        }
        if (callsEntity.getCCode() == null) {
            errorMessages.append("- Client ID\n");
            missingFields = true;
        }
        if (callsEntity.getECode() == null) {
            errorMessages.append("- Staff ID\n");
            missingFields = true;
        }
        if (callsEntity.getReqDate() == null) {
            errorMessages.append("- Request Date\n");
            missingFields = true;
        }
        if (callsEntity.getReqTime() == null) {
            errorMessages.append("- Request Time\n");
            missingFields = true;
        } else {
            if (callsEntity.getReqDate().isAfter(LocalDate.now())) {
                errorMessages.append("- Request Date cannot be in the future");
                missingFields = true;
            }
        }

        if (missingFields) {
            throw new MissingValueException(errorMessages.toString().trim());
        }

        boolean exists = callsRepository.existsById(callsEntity.getId());

        if (exists) {
            throw new DuplicateIdException("Calls record exists for given ID : " + callsEntity.getId());
        } else {

            CallsEntity savedEntity = callsRepository.save(callsEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        }
    }

    // UPDATE CALLS
    public ResponseEntity<CallsEntity> updateCalls(BigDecimal id, CallsEntity entity)
            throws ResourceNotFoundException {
        CallsEntity existingCalls = callsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No calls record exist for given id : " + id));

        if (existingCalls.isOpen()) {

            StringBuilder errorMessages = new StringBuilder("Missing Fields:\n");

            boolean missingFields = false;

            // Check for missing fields
            // Response Date not null
            if (entity.getRespDate() == null) {
                errorMessages.append("- Response Date\n");
                missingFields = true;
            } else {
                if (entity.getRespDate().isAfter(LocalDate.now())) {
                    errorMessages.append("- Response Date cannot be in the future\n");
                    missingFields = true;
                }
                if (entity.getRespDate().isBefore(existingCalls.getReqDate())) {
                    errorMessages.append("- Response Date cannot be before Request Date\n");
                    missingFields = true;
                }
            }

            // Response Time not null
            if (entity.getRespTime() == null) {
                errorMessages.append("- Response Time\n");
                missingFields = true;
            }
            // Time Arrived not null
            if (entity.getTimeArrive() == null) {
                errorMessages.append("- Time Arrived\n");
                missingFields = true;
            } else {
                if (entity.getTimeArrive().isBefore(entity.getRespTime()) && entity.getRespTime() != null) {
                    errorMessages.append("- Time Arrived cannot be before Response Time\n");
                    missingFields = true;
                }
            }
            // // Time Arrived cannot be before Response Time
            // if (entity.getTimeArrive() != null
            // && entity.getRespTime() != null
            // && entity.getTimeArrive().isBefore(entity.getRespTime())) {
            // errorMessages.append("- Time Arrived cannot be before Response Time\n");
            // missingFields = true;
            // }
            // Time Left not null
            if (entity.getTimeLeft() == null) {
                errorMessages.append("- Time Left\n");
                missingFields = true;
            } else {
                if (entity.getTimeLeft().isBefore(entity.getTimeArrive()) && entity.getTimeArrive() != null) {
                    errorMessages.append("- Time Left cannot be before Time Arrived\n");
                    missingFields = true;
                }
            }
            // // Time Left cannot be before Time Arrived
            // if (entity.getTimeLeft() != null
            // && entity.getTimeArrive() != null
            // && entity.getTimeLeft().isBefore(entity.getTimeArrive())) {
            // errorMessages.append("- Time Left cannot be before Time Arrived\n");
            // missingFields = true;
            // }

            if (missingFields) {
                throw new MissingValueException(errorMessages.toString().trim());
            }

            existingCalls.setRespDate(entity.getRespDate());
            existingCalls.setRespTime(entity.getRespTime());
            existingCalls.setTimeArrive(entity.getTimeArrive());
            existingCalls.setTimeLeft(entity.getTimeLeft());

            if (entity.getActRem1() != null) {
                existingCalls.setActRem1(entity.getActRem1());
            }
            if (entity.getActRem2() != null) {
                existingCalls.setActRem2(entity.getActRem2());
            }
            if (entity.getActRem3() != null) {
                existingCalls.setActRem3(entity.getActRem3());
            }
            if (entity.getActRem4() != null) {
                existingCalls.setActRem4(entity.getActRem4());
            }
        }

        final CallsEntity updatedCalls = callsRepository.save(existingCalls);
        return ResponseEntity.ok(updatedCalls);
    }

    // DELETE CALLS
    public ResponseEntity<CallsEntity> deleteCallsById(BigDecimal id) throws ResourceNotFoundException {
        CallsEntity calls = callsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No calls record exist for given id : " + id));

        callsRepository.delete(calls);
        return ResponseEntity.ok().build();
    }

    /*
     * Advanced search with pagination
     */
    public Page<CallsEntity> paginatedCalls(
            Pageable pageable,
            String fCat,
            Boolean open,
            String searchQuery) {

        // Start with category filter if provided
        Specification<CallsEntity> spec = Specification
                .where(CallsSpecification.hasCategory(fCat));

        // Process the search query if provided
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            searchQuery = searchQuery.trim();
            // Step 1: Search for staff and customers by name
            List<CustomerEntity> customers = customerService.searchByName(searchQuery);
            List<StaffEntity> staff = staffService.searchByName(searchQuery);

            // Step 2: Extract IDs from found staff and customers
            List<BigDecimal> customerIds = customers.stream()
                    .map(CustomerEntity::getId)
                    .collect(Collectors.toList());

            List<BigDecimal> staffIds = staff.stream()
                    .map(StaffEntity::getId)
                    .collect(Collectors.toList());

            // Step 3: Build the predicates
            Specification<CallsEntity> idSpec = null;
            if (!customerIds.isEmpty()) {
                idSpec = CallsSpecification.hasCustomerIds(customerIds);
            }

            if (!staffIds.isEmpty()) {
                Specification<CallsEntity> staffIdSpec = CallsSpecification.hasStaffIds(staffIds);
                idSpec = idSpec == null ? staffIdSpec : idSpec.or(staffIdSpec);
            }

            // Step 4: Add the search query for CallsEntity fields
            Specification<CallsEntity> searchSpec = CallsSpecification.searchByMultipleFields(searchQuery);

            // Combine the searchSpec and idSpec (if exists) using or
            if (idSpec != null) {
                spec = spec.and(searchSpec.or(idSpec));
            } else {
                spec = spec.and(searchSpec); // Only apply the search query if no IDs are found
            }
        }

        // Apply the "open" filter if provided
        if (open != null) {
            spec = spec.and(CallsSpecification.isOpen(open));
        }

        return callsRepository.findAll(spec, pageable);
    }

}
