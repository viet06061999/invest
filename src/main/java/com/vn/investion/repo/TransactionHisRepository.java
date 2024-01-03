package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.TransactionHis;
import com.vn.investion.model.define.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TransactionHisRepository extends JpaRepository<TransactionHis, Long>, JpaSpecificationExecutor<TransactionHis> {
    @Override
    @AutoAppendAuditInfo
    <S extends TransactionHis> S save(S entity);

    Optional<TransactionHis> findByIdAndStatus(Long id, TransactionStatus status);

    @Query("from TransactionHis t where t.user.phone=:phone")
    List<TransactionHis> findAllByUserPhone(String phone);

    @Query(value = "SELECT SUM(amount) FROM transaction_his " +
            "WHERE user_id IN :userIds " +
            "AND status = 1 " +
            "AND transaction_type = :transactionType " +
            "AND EXTRACT(YEAR FROM created_at) = :year " +
            "AND EXTRACT(MONTH FROM created_at) = :month", nativeQuery = true)
    Long getTotalAmountByUserIdsAndMonth(
            @Param("userIds") List<Long> userIds,
            @Param("year") int year,
            @Param("month") int month,
            int transactionType
    );

    @Query(value = " SELECT" +
            "    SUM(CASE WHEN transaction_type = 0 THEN amount ELSE 0 END) AS totalDeposit," +
            "    SUM(CASE WHEN transaction_type = 1 THEN amount ELSE 0 END) AS totalCredit" +
            "    FROM transaction_his", nativeQuery = true)
    Map<String, BigDecimal> getDashboardTransaction();
}