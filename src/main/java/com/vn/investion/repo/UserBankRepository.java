package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.UserBank;
import com.vn.investion.model.define.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBankRepository extends JpaRepository<UserBank, Long>, JpaSpecificationExecutor<UserBank> {
    @Override
    @AutoAppendAuditInfo
    <S extends UserBank> S save(S entity);

    @Query("from UserBank u where u.user.phone=:phone")
    List<UserBank> getByPhone(String phone);

    @Query("from UserBank u where u.user.role=:role")
    List<UserBank> getByRole(Role role);
}