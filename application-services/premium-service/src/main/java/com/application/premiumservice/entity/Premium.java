package com.application.premiumservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "premium", uniqueConstraints = @UniqueConstraint(columnNames = "idempotencyKey"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Premium {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String orderId;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;
    private String idempotencyKey;
}
