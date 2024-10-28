package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.Entity.StaffEntity;
import com.example.backend.Repository.StaffRepository;
import com.example.backend.Specifications.StaffSpecifications;

// import com.example.backend.Exceptions.CustomValidationException;
import com.example.backend.Exceptions.DuplicateIdException;
import com.example.backend.Exceptions.MissingValueException;
import com.example.backend.Exceptions.ResourceNotFoundException;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;

    // GET ALL STAFF
    public List<StaffEntity> getAllStaff() {
        List<StaffEntity> staffList = staffRepository.findAll();

        if (staffList.size() > 0) {
            return staffList;
        } else {
            return new ArrayList<StaffEntity>();
        }
    }

    // GET STAFF BY ID
    public ResponseEntity<StaffEntity> getStaffById(BigDecimal id) throws ResourceNotFoundException {
        StaffEntity staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No staff record exist for given id :: " + id));
        return ResponseEntity.ok().body(staff);
    }

    // CREATE STAFF
    public ResponseEntity<StaffEntity> createStaff(StaffEntity entity)
            throws DuplicateIdException {

        StringBuilder errorMessages = new StringBuilder("Required Fields:\n");

        boolean missingFields = false;

        if (entity.getId() == null) {
            errorMessages.append("- ID\n");
            missingFields = true;
        } else {
            String idString = entity.getId().toPlainString();
            if (idString.length() > 3) {
                errorMessages.append("- ID must be 3 digits or fewer\n");
                missingFields = true;
            }
            if (entity.getId().compareTo(BigDecimal.ZERO) <= 0) {
                errorMessages.append("- ID must be greater than zero\n");
                missingFields = true;
            }
        }

        if (entity.getCvlCode() == null) {
            errorMessages.append("- Civil code\n");
            missingFields = true;
        }
        if (entity.getDptCode() == null) {
            errorMessages.append("- Department Code");
            missingFields = true;
        }

        if (missingFields) {
            throw new MissingValueException(errorMessages.toString().trim());
        }

        boolean exists = staffRepository.existsById(entity.getId());

        if (exists) {
            throw new DuplicateIdException("Staff record exists for given ID : " + entity.getId());
        } else {

            StaffEntity savedEntity = staffRepository.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        }
    }

    // UPDATE STAFF
    public ResponseEntity<StaffEntity> updateStaff(BigDecimal id, StaffEntity entity) throws ResourceNotFoundException {
        StaffEntity existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No staff record exist for given id :: " + id));

        if (entity.getFirstName() != null) {
            existingStaff.setFirstName(entity.getFirstName());
        }
        if (entity.getMiddleName() != null) {
            existingStaff.setMiddleName(entity.getMiddleName());
        }
        if (entity.getLastName() != null) {
            existingStaff.setLastName(entity.getLastName());
        }
        if (entity.getTitle() != null) {
            existingStaff.setTitle(entity.getTitle());
        }
        if (entity.getCvlCode() != null) {
            existingStaff.setCvlCode(entity.getCvlCode());
        }
        if (entity.getDptCode() != null) {
            existingStaff.setDptCode(entity.getDptCode());
        }
        existingStaff.setStatus(entity.getStatus());

        final StaffEntity updatedStaff = staffRepository.save(existingStaff);
        return ResponseEntity.ok(updatedStaff);
    }

    // DELETE STAFF BY ID
    public ResponseEntity<StaffEntity> deleteStaffById(BigDecimal id) throws ResourceNotFoundException {
        StaffEntity staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No staff record exist for given id :: " + id));

        staffRepository.delete(staff);

        return ResponseEntity.ok().build();
    }

    // FILTER STAFF BY DEPARTMENT
    public List<StaffEntity> filterStaff(String dptCode) {
        Specification<StaffEntity> spec = Specification.where(StaffSpecifications.hasDepartment(dptCode));
        return staffRepository.findAll(spec);
    }

    // SEARCH STAFF BY QUERY
    public List<StaffEntity> searchStaff(String searchQuery) {
        Specification<StaffEntity> spec = StaffSpecifications.searchByMultipleFields(searchQuery);
        return staffRepository.findAll(spec);
    }

    // FILTER AND SEARCH STAFF
    public List<StaffEntity> filterAndSearchStaff(String dptCode, String searchQuery) {
        Specification<StaffEntity> spec = Specification
                .where(StaffSpecifications.hasDepartment(dptCode))
                .and(StaffSpecifications.searchByMultipleFields(searchQuery));

        return staffRepository.findAll(spec);
    }

    /*
     * Advanced search with pagination
     */
    public Page<StaffEntity> paginatedStaff(Pageable pageable, String dptCode, String searchQuery) {
        Specification<StaffEntity> spec = Specification
                .where(StaffSpecifications.hasDepartment(dptCode))
                .and(StaffSpecifications.searchByMultipleFields(searchQuery));

        return staffRepository.findAll(spec, pageable);
    }

    /*
     * Search by name
     */
    public List<StaffEntity> searchByName(String searchQuery) {
        Specification<StaffEntity> spec = StaffSpecifications.searchByName(searchQuery);
        return staffRepository.findAll(spec);
    }
}
