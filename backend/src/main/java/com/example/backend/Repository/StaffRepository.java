package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.Entity.StaffEntity;
import java.math.BigDecimal;

public interface StaffRepository extends JpaRepository<StaffEntity, BigDecimal>{
	
}