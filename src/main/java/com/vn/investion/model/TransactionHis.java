package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.TransactionStatus;
import com.vn.investion.model.define.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
    private long amount;
    @Column(length = 1024)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long remainDepositBalance;
    private long remainAvailableBalance;
    public TransactionHis copy() {
        TransactionHis copy = new TransactionHis();
        copy.setId(this.id);
        copy.setTransactionType(this.transactionType);
        copy.setStatus(this.status);
        copy.setNumberAccount(this.numberAccount);
        copy.setAccountName(this.accountName);
        copy.setBank(this.bank);
        copy.setAmount(this.amount);
        copy.setDescription(this.description);
        copy.setUser(this.user);
        copy.setRemainDepositBalance(this.remainDepositBalance);
        copy.setRemainAvailableBalance(this.remainAvailableBalance);
        return copy;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TransactionHis other = (TransactionHis) obj;
        return Objects.equals(id, other.id) &&
                transactionType == other.transactionType &&
                status == other.status &&
                Objects.equals(numberAccount, other.numberAccount) &&
                Objects.equals(accountName, other.accountName) &&
                Objects.equals(bank, other.bank) &&
                Objects.equals(amount, other.amount) &&
                Objects.equals(description, other.description) &&
                Objects.equals(user, other.user) &&
                Objects.equals(remainDepositBalance, other.remainDepositBalance)&&
                Objects.equals(remainAvailableBalance, other.remainAvailableBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                transactionType,
                status,
                numberAccount,
                accountName,
                bank,
                amount,
                description,
                user,
                remainDepositBalance,
                remainAvailableBalance);
    }
}
