package com.vn.investion.repo;

import com.vn.investion.model.TransactionHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionHisRepository extends JpaRepository<TransactionHis, Long>, JpaSpecificationExecutor<TransactionHis> {

}