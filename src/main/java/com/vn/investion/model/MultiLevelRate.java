package com.vn.investion.model;

import com.vn.investion.model.define.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "multi_level_rate")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiLevelRate extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 8)
    private String level;
    private Double rate;
}
