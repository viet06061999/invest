package com.vn.investion.repo;

import com.vn.investion.model.UserLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserLeaderRepository extends JpaRepository<UserLeader, Long>, JpaSpecificationExecutor<UserLeader> {

}