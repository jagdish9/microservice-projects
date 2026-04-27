package com.microscopic.employeeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_data")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;
    private double salary;
    private String department;
    private String idempotencyKey;
}
