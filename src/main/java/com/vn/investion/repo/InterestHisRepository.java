package com.vn.investion.repo;

import com.vn.investion.model.InterestHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InterestHisRepository extends JpaRepository<InterestHis, Long>, JpaSpecificationExecutor<InterestHis> {

}