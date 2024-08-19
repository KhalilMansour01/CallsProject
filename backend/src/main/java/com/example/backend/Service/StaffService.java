package com.example.backend.Service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.StaffEntity;
import com.example.backend.Repository.StaffRepository;
import com.example.backend.Exceptions.RecordNotFoundException;

@Service
public class StaffService {
    @Autowired
    StaffRepository repository;

    public List<StaffEntity> getAllStaff() {
        List<StaffEntity> staffList = repository.findAll();

        if (staffList.size() > 0) {
            return staffList;
        } else {
            return new ArrayList<StaffEntity>();
        }
    }

    public StaffEntity getStaffById(BigDecimal id) throws RecordNotFoundException {
        Optional<StaffEntity> staff = repository.findById(id);

        if (staff.isPresent()) {
            return staff.get();
        } else {
            throw new RecordNotFoundException("No staff record exist for given id");
        }
    }    

    public StaffEntity createStaff(StaffEntity entity) throws RecordNotFoundException {
        Optional<StaffEntity> staff = repository.findById(entity.getId());
        if(staff.isPresent()){
            throw new RecordNotFoundException("Staff record exist for given id");
        } else {
            return repository.save(entity);
        }
    }

    public StaffEntity updateStaff(BigDecimal id, StaffEntity entity) throws RecordNotFoundException {
        StaffEntity existingStaff = repository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException("No staff record exist for given id"));

        if (entity.getF_name() != null) {
            existingStaff.setF_name(entity.getF_name());
        }
        if (entity.getM_name() != null) {
            existingStaff.setM_name(entity.getM_name());
        }
        if (entity.getL_name() != null) {
            existingStaff.setL_name(entity.getL_name());
        }
        if (entity.getTitle() != null) {
            existingStaff.setTitle(entity.getTitle());
        }
        if (entity.getCvl_code() != null) {
            existingStaff.setCvl_code(entity.getCvl_code());
        }
        if (entity.getDpt_code() != null) {
            existingStaff.setDpt_code(entity.getDpt_code());
        }

        return repository.save(existingStaff);
    }

    public void deleteStaffById(BigDecimal id) throws RecordNotFoundException {
        Optional<StaffEntity> staff = repository.findById(id);

        if (staff.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
