package com.vn.investion.dto.search.transaction;

import com.vn.investion.model.TransactionHis;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class TransactionSpec implements Specification<TransactionHis> {

    private final TransactionFilter transactionFilter;

    @Override
    public Predicate toPredicate
            (Root<TransactionHis> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (transactionFilter == null) {
            return builder.conjunction();
        }
        var predicate = builder.conjunction();
        if (transactionFilter.getStatus() != null) {
            predicate = builder.and(predicate, builder.equal(
                    root.get("status"), transactionFilter.getStatus()));
        }
        if (transactionFilter.getType() != null) {
            predicate = builder.and(predicate, builder.equal(
                    root.get("transactionType"), transactionFilter.getType()));
        }
        return predicate;
    }
}
