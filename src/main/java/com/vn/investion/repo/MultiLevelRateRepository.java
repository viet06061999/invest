package com.vn.investion.repo;

import com.vn.investion.model.MultiLevelRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MultiLevelRateRepository extends JpaRepository<MultiLevelRate, Long>, JpaSpecificationExecutor<MultiLevelRate> {

}