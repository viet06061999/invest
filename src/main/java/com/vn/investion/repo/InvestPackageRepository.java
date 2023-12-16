package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.InvestPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestPackageRepository extends JpaRepository<InvestPackage, Long>, JpaSpecificationExecutor<InvestPackage> {

    @Override
    @AutoAppendAuditInfo
    <S extends InvestPackage> S save(S entity);

    @Override
    @AutoAppendAuditInfo
    <S extends InvestPackage> S saveAndFlush(S entity);

    Optional<InvestPackage> findByIdAndIsActiveTrue(Long id);

    List<InvestPackage> findAllByIsActiveTrue();
}