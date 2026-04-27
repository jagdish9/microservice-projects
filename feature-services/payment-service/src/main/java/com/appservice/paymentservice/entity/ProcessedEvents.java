package com.appservice.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_app_events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProcessedEvents {

    @Id
    private String eventId;
}
