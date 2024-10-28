package com.example.backend.Specifications;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import com.example.backend.Entity.StaffEntity;

public class StaffSpecifications {

    // Specification to filter by Department
    public static Specification<StaffEntity> hasDepartment(String dptCode) {
        return (root, query, criteriaBuilder) -> 
        dptCode == null || dptCode.isEmpty() ? 
            criteriaBuilder.conjunction() : 
            criteriaBuilder.equal(criteriaBuilder.lower(root.get("dptCode")), dptCode.toLowerCase());
    }

    // Specification to search by multiple fields
    public static Specification<StaffEntity> searchByMultipleFields(String searchQuery){
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String lowerSearchQuery = searchQuery.toLowerCase();

            Predicate idPredicate = criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.toString(root.get("id"))), "%" + lowerSearchQuery + "%");
            Predicate firstNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + lowerSearchQuery + "%");
            Predicate middleNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), "%" + lowerSearchQuery + "%");
            Predicate lastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lowerSearchQuery + "%");

            Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + lowerSearchQuery + "%");
          
            return criteriaBuilder.or(
                idPredicate, 
                firstNamePredicate, 
                middleNamePredicate,
                lastNamePredicate,
                titlePredicate
               );
        };
    }

    public static Specification<StaffEntity> searchByName(String searchQuery){
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String lowerSearchQuery = searchQuery.toLowerCase();

            Predicate firstNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + lowerSearchQuery + "%");
            Predicate middleNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), "%" + lowerSearchQuery + "%");
            Predicate lastNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lowerSearchQuery + "%");

            return criteriaBuilder.or(
                firstNamePredicate, 
                middleNamePredicate,
                lastNamePredicate
               );
        };
    }
}
