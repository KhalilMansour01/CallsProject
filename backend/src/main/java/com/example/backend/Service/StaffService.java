package com.example.backend.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.Entity.StaffEntity;
import com.example.backend.Repository.StaffRepository;

@Service
public class StaffService {
    @Autowired
    StaffRepository repository;

    public List<StaffEntity> getAllStaff(){
        List<StaffEntity> staffList = repository.findAll();
        
        if(staffList.size() > 0) {
            return staffList;
        } else {
            return new ArrayList<StaffEntity>();
        }
    }
}
