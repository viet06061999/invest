package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<UserNotification, Long>, JpaSpecificationExecutor<UserNotification> {
    @Override
    @AutoAppendAuditInfo
    <S extends UserNotification> S save(S entity);

    @Query(value = "SELECT * from user_noti WHERE user_id isnull or user_id = :userId", nativeQuery = true)
    List<UserNotification> findAllByUserId(Long userId);
}