package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.UserLeader;
import com.vn.investion.model.define.UserPackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLeaderRepository extends JpaRepository<UserLeader, Long>, JpaSpecificationExecutor<UserLeader> {
    @Override
    @AutoAppendAuditInfo
    <S extends UserLeader> S save(S entity);

    @Query("FROM UserLeader u where u.user.phone=:phone")
    List<UserLeader> findAllByPhone(String phone);

    @Query("FROM UserLeader u where u.user.id=:userId and u.status=:status")
    List<UserLeader> findAllByUserStatus(Long userId, UserPackageStatus status);

    @Query("FROM UserLeader u where u.user.id=:userId and u.leaderPackage.id=:leaderId")
    List<UserLeader> findAllByUserAndLeader(Long userId, Long leaderId);

    Optional<UserLeader> findByIdAndStatus(Long id, UserPackageStatus status);
}
