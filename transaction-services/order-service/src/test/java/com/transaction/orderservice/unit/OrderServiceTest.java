package com.transaction.orderservice.unit;

import com.transaction.commonevents.events.OrderCreatedEvent;
import com.transaction.orderservice.entity.Order;
import com.transaction.orderservice.repository.OrderRepository;
import com.transaction.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private OrderService service;

    @Test
    void shouldCreateOrderSuccessfully() {
        Order order = new Order(123L, "O12", 20, "CREATED");

        when(repository.save(any())).thenReturn(order);

        Order result = service.createOrder(order);

        assertNotNull(result);
        assertEquals(20, result.getQuantity());

        verify(repository, times(1)).save(order);

        // Verify Kafka call
        ArgumentCaptor<OrderCreatedEvent> eventCaptor =
                ArgumentCaptor.forClass(OrderCreatedEvent.class);

        verify(kafkaTemplate, times(1))
                .send(eq("order-topic"), eventCaptor.capture());
    }

    @Test
    void shouldSetStatusCreated() {
        Order order = new Order();
        order.setId(2L);
        order.setProductId("PO21");
        order.setQuantity(50);

        when(repository.save(any(Order.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Order result = service.createOrder(order);

        assertEquals("CREATED", result.getStatus());
    }

    @Test
    void kafkaFailureShouldThrowException() {
        Order order = new Order();
        order.setId(2L);
        order.setProductId("PO21");
        order.setQuantity(50);

        Order savedOrder = new Order();
        order.setId(3L);

        when(repository.save(any())).thenReturn(savedOrder);

        doThrow(new RuntimeException("Kafka down"))
                .when(kafkaTemplate)
                .send(anyString(), any());

        assertThrows(RuntimeException.class, () -> {
            service.createOrder(order);
        });

        verify(repository, times(1)).save(any());
    }

    @Test
    void getCreatedOrderSuccess() {
        Order order = new Order();
        order.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(order));

        Order result = service.getCreatedOrder(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repository).findById(1L);
    }

/*    @Test
    void shouldCreateOrder() {
        Order order = new Order();
        order.setId(4L);
        order.setQuantity(30);
        order.setProductId("Od-45");

        service.createOrder(order);

        verify(repository, times(1)).save(any(Order.class));

        verify(kafkaTemplate, times(1)).send(eq("order-topic"), any());
    }*/
}

/*
What is happening?
@Mock → fake DB
@InjectMocks → inject mock into service
when() → define behavior
verify() → ensure method called
 */