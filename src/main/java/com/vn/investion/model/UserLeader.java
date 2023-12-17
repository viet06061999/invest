package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.InvestType;
import com.vn.investion.model.define.UserPackageStatus;
import com.vn.investion.utils.DateTimeUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_leader")
public class UserLeader extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer duration;
    Long amt;
    InvestType investType;
    Double rate;
    OffsetDateTime interestDate;
    OffsetDateTime withdrawDate;
    UserPackageStatus status = UserPackageStatus.INVESTING;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "package_id")
    LeaderPackage leaderPackage;

    public Long getCurrentInterest(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime minDateTime = withdrawDate.isBefore(now) ? withdrawDate : now;
        var durationCount = DateTimeUtils.getCountInterest(investType, interestDate, minDateTime);
        return (long) (amt*rate*durationCount);
    }

    public long getInvestDuration(){
        return DateTimeUtils.getCountInterest(investType, getCreatedAt(), OffsetDateTime.now());
    }
}