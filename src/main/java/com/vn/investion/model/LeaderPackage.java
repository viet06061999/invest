package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import com.vn.investion.model.define.InvestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "leader_package")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderPackage extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer duration;
    private Long amt;
    private InvestType investType;
    private Double rate;
    @Column(length = 256)
    private String title;
    @Column(length = 5000)
    private String description;
    private Boolean isActive;
    private String image;
    private Long remainBuy;
    private Long userCanBuy;
}
