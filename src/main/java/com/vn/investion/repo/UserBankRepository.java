package com.vn.investion.repo;

import com.vn.investion.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserBankRepository extends JpaRepository<UserBank, Long>, JpaSpecificationExecutor<UserBank> {

}