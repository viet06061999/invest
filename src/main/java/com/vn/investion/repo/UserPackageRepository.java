package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.UserPackage;
import com.vn.investion.model.define.UserPackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long>, JpaSpecificationExecutor<UserPackage> {

    @Override
    @AutoAppendAuditInfo
    <S extends UserPackage> S save(S entity);


    @Query("FROM UserPackage u where u.user.phone=:phone")
    List<UserPackage> findAllByPhone(String phone);

    Optional<UserPackage> findByIdAndStatus(Long id, UserPackageStatus status);

    @Query("FROM UserPackage u where u.user.id=:userId and u.investPackage.id=:packageId")
    List<UserPackage> findAllByUserAndLeader(Long userId, Long packageId);

}