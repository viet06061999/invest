package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "payslip_his")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayslipHis extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer totalMember;
    private Integer totalF1;
    private Long totalDeposit;
    private Integer level;
    private double progress;
}
