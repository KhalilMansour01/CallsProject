package com.example.backend.Specifications;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import com.example.backend.Entity.CustomerEntity;

public class CustomerSpecifications {
    
    // Specification to filter by Region
    public static Specification<CustomerEntity> hasRegion(String regCode) {
        return (root, query, criteriaBuilder) -> 
            regCode == null || regCode.isEmpty() ? 
            criteriaBuilder.conjunction() : 
            criteriaBuilder.equal(criteriaBuilder.lower(root.get("regCode")), regCode.toLowerCase());
    }

    // Specification to filter by Country
    public static Specification<CustomerEntity> hasCountry(String cntrCode) {
        return (root, query, criteriaBuilder) -> 
            cntrCode == null || cntrCode.isEmpty() ? 
            criteriaBuilder.conjunction() : 
            criteriaBuilder.equal(criteriaBuilder.lower(root.get("cntrCode")), cntrCode.toLowerCase());
    }

    // Specification to search by multiple fields
    public static Specification<CustomerEntity> searchByMultipleFields(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return criteriaBuilder.conjunction(); // Return true if no search query
            }

            String lowerSearchQuery = searchQuery.toLowerCase();

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.get("id"))), "%" + lowerSearchQuery + "%");
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("custName")), "%" + lowerSearchQuery + "%");
            Predicate tel1Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("tel1")), "%" + lowerSearchQuery + "%");
            Predicate tel2Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("tel2")), "%" + lowerSearchQuery + "%");
            Predicate tel3Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("tel3")), "%" + lowerSearchQuery + "%");
            Predicate faxPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("fax")), "%" + lowerSearchQuery + "%");
            Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + lowerSearchQuery + "%");
            Predicate contactPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("contact")), "%" + lowerSearchQuery + "%");

            return criteriaBuilder.or(
                idPredicate, 
                namePredicate, 
                tel1Predicate, 
                tel2Predicate, 
                tel3Predicate, 
                faxPredicate, 
                emailPredicate, 
                contactPredicate);
        };
    }

}
