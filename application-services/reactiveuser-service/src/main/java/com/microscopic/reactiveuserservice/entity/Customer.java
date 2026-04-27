package com.microscopic.reactiveuserservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "t_customer")
@Getter
@Setter
public class Customer {

    @Id
    private Long id;
    private String name;
    private String email;
}
