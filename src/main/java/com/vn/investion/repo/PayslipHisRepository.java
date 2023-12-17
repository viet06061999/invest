package com.vn.investion.repo;

import com.vn.investion.audit.AutoAppendAuditInfo;
import com.vn.investion.model.PayslipHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayslipHisRepository extends JpaRepository<PayslipHis, Long>, JpaSpecificationExecutor<PayslipHis> {
    @Override
    @AutoAppendAuditInfo
    <S extends PayslipHis> S save(S entity);

    @Query(value = "select * from payslip_his i " +
            " WHERE user_id=:userId"+
            " AND EXTRACT(YEAR FROM created_at) = :year " +
            " AND EXTRACT(MONTH FROM created_at) = :month", nativeQuery = true)
    Optional<PayslipHis> getPayslipHisMonth(Long userId,
                                           @Param("year") int year,
                                           @Param("month") int month);

    @Query(value = "from PayslipHis p " +
            " WHERE p.user.phone=:phone")
    List<PayslipHis> getPayslipUser(String phone);
}