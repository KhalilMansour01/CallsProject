package com.example.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.Entity.CustomerEntity;
import java.math.BigDecimal;

public interface CustomerRepository extends JpaRepository<CustomerEntity, BigDecimal> {
    
}
