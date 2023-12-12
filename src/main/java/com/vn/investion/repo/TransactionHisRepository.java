package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.TransactionHis;
import com.vn.investion.model.define.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionHisRepository extends JpaRepository<TransactionHis, Long>, JpaSpecificationExecutor<TransactionHis> {
    @Override
    @AutoAppendAuditInfo
    <S extends TransactionHis> S save(S entity);

    Optional<TransactionHis> findByIdAndStatus(Long id, TransactionStatus status);

    @Query("from TransactionHis t where t.user.phone=:phone")
    List<TransactionHis> findAllByUserPhone(String phone);
}