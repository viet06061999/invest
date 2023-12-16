package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByPhone(String phone);

    Optional<User> findByCode(String code);

    @Override
    @AutoAppendAuditInfo
    <S extends User> S save(S entity);

    @Query(value = "WITH RECURSIVE UserHierarchy AS (" +
            "    SELECT code, ref_id, 1 AS level " +
            "    FROM users" +
            "    WHERE ref_id =:refId" +
            "" +
            "    UNION ALL " +
            "" +
            "    SELECT u.code, u.ref_id, level + 1" +
            "    FROM users u" +
            "             INNER JOIN UserHierarchy h ON u.ref_id = h.code " +
            "    WHERE level < :level " +
            ")" +
            " SELECT h.*, u.* " +
            " FROM UserHierarchy h " +
            "         JOIN users u ON h.code = u.code;", nativeQuery = true)
    List<Object[]> findUserHierarchy(String refId, int level);

    @Query(value = "WITH RECURSIVE UserHierarchy AS (" +
            "    SELECT code, ref_id, 0 AS level " +
            "    FROM users" +
            "    WHERE code =:code" +
            "" +
            "    UNION ALL " +
            "" +
            "    SELECT u.code, u.ref_id, level + 1" +
            "    FROM users u" +
            "             INNER JOIN UserHierarchy h ON u.code = h.ref_id " +
            "    WHERE level < :level " +
            ")" +
            " SELECT h.*, u.* " +
            " FROM UserHierarchy h " +
            "         JOIN users u ON h.code = u.code;", nativeQuery = true)
    List<Object[]> findParentHierarchy(String code, int level);
}
