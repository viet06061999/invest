package com.vn.investion.dto.filter;

import com.vn.investion.model.TransactionHis;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;

public class TransactionFilters {
    private TransactionFilters() {
    }

    public static Specification<TransactionHis> toSpecificationAccount(TransactionFilter filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filter == null) {
                return predicate;
            }

            if (filter.getTransactionType() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("transactionType"),
                        Enum.valueOf(TransactionType.class, filter.getTransactionType())));
            }

            if (filter.getStatus() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"),
                        Enum.valueOf(TransactionStatus.class, filter.getStatus())));
            }

            if (filter.getAmountFrom() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("amount"),
                        filter.getAmountFrom()));
            }

            if (filter.getAmountTo() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("amount"),
                        filter.getAmountFrom()));
            }

            if (filter.getUpdatedAtTo() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("updatedAt"),
                        OffsetDateTime.parse(filter.getUpdatedAtTo())));
            }

            if (filter.getUpdatedBy() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("updatedBy"), filter.getUpdatedBy()));
            }

            if (filter.getUpdatedAtFrom() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("updatedAt"),
                        OffsetDateTime.parse(filter.getUpdatedAtFrom())));
            }

            if (filter.getUpdatedAtTo() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("updatedAt"),
                        OffsetDateTime.parse(filter.getUpdatedAtTo())));
            }

            return predicate;
        };
    }
}

