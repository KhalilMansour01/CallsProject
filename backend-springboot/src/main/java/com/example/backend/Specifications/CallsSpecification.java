package com.example.backend.Specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.backend.Entity.CallsEntity;

import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.List;

public class CallsSpecification {

        // Specification to filter by category
        public static Specification<CallsEntity> hasCategory(String fCat) {
                return (root, query, criteriaBuilder) -> fCat == null || fCat.isEmpty() ? criteriaBuilder.conjunction()
                                : criteriaBuilder.equal(criteriaBuilder.lower(root.get("fCat")), fCat.toLowerCase());

        };

        // Specification to filter by open status
        public static Specification<CallsEntity> isOpen(Boolean open) {
                return (root, query, criteriaBuilder) -> {
                        Predicate isOpenPredicate = criteriaBuilder.and(
                                        criteriaBuilder.isNull(root.get("timeLeft")),
                                        criteriaBuilder.isNull(root.get("timeArrive")));

                        Predicate isClosedPredicate = criteriaBuilder.and(
                                        criteriaBuilder.isNotNull(root.get("timeLeft")),
                                        criteriaBuilder.isNotNull(root.get("timeArrive")));

                        return open != null && open ? isOpenPredicate : isClosedPredicate;
                };
        }

        // Specification to filter by staff IDs (matches any ID in the list)
        public static Specification<CallsEntity> hasStaffIds(List<BigDecimal> staffIds) {
                return (root, query, criteriaBuilder) -> root.get("eCode").in(staffIds);
        }

        // Specification to filter by customer IDs (matches any ID in the list)
        public static Specification<CallsEntity> hasCustomerIds(List<BigDecimal> customerIds) {
                return (root, query, criteriaBuilder) -> root.get("cCode").in(customerIds);
        }

        // Specification to search by multiple fields
        public static Specification<CallsEntity> searchByMultipleFields(String searchQuery) {
                return (root, query, criteriaBuilder) -> {
                        if (searchQuery == null || searchQuery.isEmpty()) {
                                return criteriaBuilder.conjunction();
                        }

                        String lowerSearchQuery = searchQuery.toLowerCase();

                        // BigDecimals are converted to Strings
                        Predicate idPredicate = criteriaBuilder.like(
                                        criteriaBuilder.lower(criteriaBuilder.toString(root.get("id"))),
                                        "%" + lowerSearchQuery + "%");
                        Predicate cCodePredicate = criteriaBuilder.like(
                                        criteriaBuilder.lower(criteriaBuilder.toString(root.get("cCode"))),
                                        "%" + lowerSearchQuery + "%");
                        Predicate eCodePredicate = criteriaBuilder.like(
                                        criteriaBuilder.lower(criteriaBuilder.toString(root.get("eCode"))),
                                        "%" + lowerSearchQuery + "%");
                        Predicate rem1Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("rem1")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate rem2Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("rem2")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate rem3Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("rem3")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate rem4Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("rem4")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate actRem1Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("actRem1")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate actRem2Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("actRem2")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate actRem3Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("actRem3")),
                                        "%" + lowerSearchQuery + "%");
                        Predicate actRem4Predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("actRem4")),
                                        "%" + lowerSearchQuery + "%");

                        return criteriaBuilder.or(
                                        idPredicate,
                                        cCodePredicate,
                                        eCodePredicate,
                                        rem1Predicate,
                                        rem2Predicate,
                                        rem3Predicate,
                                        rem4Predicate,
                                        actRem1Predicate,
                                        actRem2Predicate,
                                        actRem3Predicate,
                                        actRem4Predicate);
                };
        }
}
