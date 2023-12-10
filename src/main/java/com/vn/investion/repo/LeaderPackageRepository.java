package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.LeaderPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LeaderPackageRepository extends JpaRepository<LeaderPackage, Long>, JpaSpecificationExecutor<LeaderPackage> {
    @Override
    @AutoAppendAuditInfo
    <S extends LeaderPackage> S save(S entity);

    Optional<LeaderPackage> findByIdAndIsActiveTrue(Long id);

    List<LeaderPackage> findAllByIsActiveTrue();
}