package com.example.order.controller;

import com.example.order.config.AppConfig;
import com.example.order.dto.OrderDto;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for OrderController.
 * Tests the controller logic with mocked service (no Spring framework overhead).
 */
class OrderControllerTest {

    @Test
    void getById_returnsOrder() {
        OrderService service = Mockito.mock(OrderService.class);
        AppConfig appConfig = Mockito.mock(AppConfig.class);

        OrderController controller = new OrderController(service, appConfig);

        OrderDto order = OrderDto.builder()
                .id(5L)
                .orderNumber("ORD-2026-005")
                .userId(2L)
                .productId(3L)
                .quantity(2)
                .unitPrice(new BigDecimal("49.99"))
                .totalPrice(new BigDecimal("99.98"))
                .build();

        when(service.getById(5L)).thenReturn(Optional.of(order));

        var response = controller.getById(5L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
        assertEquals("ORD-2026-005", response.getBody().getOrderNumber());
    }

    @Test
    void getById_returnsNotFoundWhenNotExists() {
        OrderService service = Mockito.mock(OrderService.class);
        AppConfig appConfig = Mockito.mock(AppConfig.class);

        OrderController controller = new OrderController(service, appConfig);

        when(service.getById(99L)).thenReturn(Optional.empty());

        var response = controller.getById(99L);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getByOrderNumber_found() {
        OrderService service = Mockito.mock(OrderService.class);
        AppConfig appConfig = Mockito.mock(AppConfig.class);

        OrderController controller = new OrderController(service, appConfig);

        OrderDto order = OrderDto.builder()
                .id(3L)
                .orderNumber("ORD-2026-003")
                .userId(1L)
                .productId(1L)
                .quantity(2)
                .unitPrice(new BigDecimal("29.99"))
                .totalPrice(new BigDecimal("59.98"))
                .build();

        when(service.getByOrderNumber("ORD-2026-003")).thenReturn(Optional.of(order));

        var response = controller.getByOrderNumber("ORD-2026-003");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getId());
    }
}


