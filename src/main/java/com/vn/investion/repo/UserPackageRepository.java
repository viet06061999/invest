package com.vn.investion.repo;

import com.vn.investion.model.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserPackageRepository extends JpaRepository<UserPackage, Long>, JpaSpecificationExecutor<UserPackage> {

}