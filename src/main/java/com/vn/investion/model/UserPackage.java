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
@Table(name = "user_package")
public class UserPackage extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer duration;
    long amt;
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
    InvestPackage investPackage;

    public long getCurrentInterest(){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime minDateTime = withdrawDate.isBefore(now) ? withdrawDate : now;
        OffsetDateTime minIntDateTime = interestDate.isBefore(minDateTime) ? interestDate : minDateTime;
        var durationCount = DateTimeUtils.getCountInterest(investType, minIntDateTime, minDateTime);
        return (long)(amt*rate*durationCount);
    }

    public long getInvestDuration(){
        return DateTimeUtils.getCountInterest(investType, getCreatedAt(), OffsetDateTime.now());
    }
}