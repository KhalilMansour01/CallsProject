package com.example.backend.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.backend.Entity.CallsEntity;
import java.math.BigDecimal;

@Repository
public interface CallsRepository extends JpaRepository<CallsEntity, BigDecimal>, JpaSpecificationExecutor<CallsEntity> {
	
    @NonNull
    Page<CallsEntity> findAll(@NonNull Pageable pageable);
}
