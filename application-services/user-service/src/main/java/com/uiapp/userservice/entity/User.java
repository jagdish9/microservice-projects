package com.uiapp.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ui_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Integer age;
}
