package com.retrymechanism.commonevents.events;

import com.retrymechanism.commonevents.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FailedEvent {

    private String eventId;
    private OrderDto payload;
    private int retryCount;
    private String status;
    private LocalDateTime createdAt;
}
