package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "invest_his")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestHis extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long amount;

    private long interestAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private LeaderPackage leaderPackage;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private InvestPackage investPackage;

    @ManyToOne
    @JoinColumn(name = "ref_id")
    private User refUser;

    private long remainAvailableBalance;
}
