package com.appservice.effectiverestapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_era_emp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private int age;

    private String department;

    private double salary;

    private String mobile;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "idempotency-key")
    private String idempotencyKey;
}
