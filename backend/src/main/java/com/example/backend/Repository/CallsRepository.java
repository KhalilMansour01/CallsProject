package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.Entity.CallsEntity;
import java.math.BigDecimal;

public interface CallsRepository extends JpaRepository<CallsEntity, BigDecimal> {
	
}
