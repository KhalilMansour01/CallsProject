package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.backend.Entity.StaffEntity;
import java.math.BigDecimal;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, BigDecimal>, JpaSpecificationExecutor<StaffEntity> {


}