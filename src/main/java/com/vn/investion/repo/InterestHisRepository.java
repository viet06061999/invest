package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.InterestHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestHisRepository extends JpaRepository<InterestHis, Long>, JpaSpecificationExecutor<InterestHis> {
    @Override
    @AutoAppendAuditInfo
    <S extends InterestHis> S save(S entity);

    @Query("from InterestHis i WHERE i.user.phone=:phone")
    List<InterestHis> getInterestHisByPhone(String phone);
}