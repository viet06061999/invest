package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "transaction_his")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHis extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private TransactionType transactionType;
    private TransactionStatus status;
    @Column(length = 20)
    private String numberAccount;
    @Column(length = 128)
    private String accountName;
    @Column(length = 128)
    private String bank;
    private Double amount;
    @Column(length = 1024)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
