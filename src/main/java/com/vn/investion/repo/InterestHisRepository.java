package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.InvestHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestHisRepository extends JpaRepository<InvestHis, Long>, JpaSpecificationExecutor<InvestHis> {
    @Override
    @AutoAppendAuditInfo
    <S extends InvestHis> S save(S entity);

    @Query("from InvestHis i WHERE i.user.phone=:phone")
    List<InvestHis> getInterestHisByPhone(String phone);
}