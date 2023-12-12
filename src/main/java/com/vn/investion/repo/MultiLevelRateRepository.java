package com.vn.investion.repo;

import com.vn.investion.model.MultiLevelRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MultiLevelRateRepository extends JpaRepository<MultiLevelRate, Long>, JpaSpecificationExecutor<MultiLevelRate> {
    Optional<MultiLevelRate> findByLevel(Integer level);
}