package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.CallsEntity;
import com.example.backend.Exceptions.DuplicateIdException;
import com.example.backend.Exceptions.MissingValueException;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.Repository.CallsRepository;
import com.example.backend.Specifications.CallsSpecification;

@Service
public class CallsService {
    @Autowired
    CallsRepository callsRepository;

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
            throws ResourceNotFoundException, DuplicateIdException {

        StringBuilder errorMessages = new StringBuilder();

        if (callsEntity.getId() == null) {
            errorMessages.append("Id is required \n");
        }
        if (callsEntity.getCCode() == null) {
            errorMessages.append("Client Id is required \n");
        }
        if (callsEntity.getECode() == null) {
            errorMessages.append("Staff Id is required \n");
        }
        if (callsEntity.getReqDate() == null) {
            errorMessages.append("Request Date is required \n");
        }
        if (callsEntity.getReqTime() == null) {
            errorMessages.append("Request Time is required \n");
        }

        if (errorMessages.length() > 0) {
            throw new ResourceNotFoundException(errorMessages.toString().trim());
        }

        boolean exists = callsRepository.existsById(callsEntity.getId());

        if (exists) {
            throw new DuplicateIdException("Calls record exists for given id : " + callsEntity.getId());
        } else {

            CallsEntity savedEntity = callsRepository.save(callsEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        }
    }

    // UPDATE CALLS
    public ResponseEntity<CallsEntity> updateCalls(BigDecimal id, CallsEntity callsEntity)
            throws ResourceNotFoundException {
        CallsEntity existingCalls = callsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No calls record exist for given id : " + id));

        if (existingCalls.isOpen()) {

            StringBuilder errorMessages = new StringBuilder();

            if (callsEntity.getRespDate() == null) {
                errorMessages.append("Response Date is required \n");
            }
            if (callsEntity.getRespTime() == null) {
                errorMessages.append("Response Time is required \n");
            }
            if (callsEntity.getTimeArrive() == null) {
                errorMessages.append("Time Arrived is required \n");
            }
            if (callsEntity.getTimeLeft() == null) {
                errorMessages.append("Time Left is required \n");
            }

            if (errorMessages.length() > 0) {
                throw new MissingValueException(errorMessages.toString().trim());
            }

            existingCalls.setRespDate(callsEntity.getRespDate());
            existingCalls.setRespTime(callsEntity.getRespTime());
            existingCalls.setTimeArrive(callsEntity.getTimeArrive());
            existingCalls.setTimeLeft(callsEntity.getTimeLeft());

            if (callsEntity.getActRem1() != null) {
                existingCalls.setActRem1(callsEntity.getActRem1());
            }
            if (callsEntity.getActRem2() != null) {
                existingCalls.setActRem2(callsEntity.getActRem2());
            }
            if (callsEntity.getActRem3() != null) {
                existingCalls.setActRem3(callsEntity.getActRem3());
            }
            if (callsEntity.getActRem4() != null) {
                existingCalls.setActRem4(callsEntity.getActRem4());
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

    // FILTER CALLS
    public List<CallsEntity> filterCalls(String fCat, Boolean open) {
        Specification<CallsEntity> spec = Specification.where(CallsSpecification.hasCategory(fCat))
                .and(CallsSpecification.isOpen(open));
        return callsRepository.findAll(spec);
    }

    // SEARCH CALLS
    public List<CallsEntity> searchCalls(String searchQuery) {
        Specification<CallsEntity> spec = CallsSpecification.searchByMultipleFields(searchQuery);
        return callsRepository.findAll(spec);
    }

    // FILTER AND SEARCH CALLS
    public List<CallsEntity> filterAndSearchCalls(String fCat, Boolean open, String searchQuery) {

        Specification<CallsEntity> spec = Specification
                .where(CallsSpecification.hasCategory(fCat))
                .and(CallsSpecification.searchByMultipleFields(searchQuery));

        // Only apply the 'open' filter if it is explicitly provided
        if (open != null) {
            spec = spec.and(CallsSpecification.isOpen(open));
        }

        return callsRepository.findAll(spec);
    }
}
