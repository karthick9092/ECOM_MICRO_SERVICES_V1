package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository repo;
    private OrderService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(OrderRepository.class);
        service = new OrderServiceImpl(repo);
    }

    @Test
    void create_success() {
        OrderDto dto = OrderDto.builder()
                .orderNumber("ORD-2026-001")
                .userId(1L)
                .productId(1L)
                .quantity(5)
                .unitPrice(new BigDecimal("19.99"))
                .totalPrice(new BigDecimal("99.95"))
                .build();

        when(repo.existsByOrderNumber("ORD-2026-001")).thenReturn(false);
        Order saved = Order.builder()
                .id(1L)
                .orderNumber("ORD-2026-001")
                .userId(1L)
                .productId(1L)
                .quantity(5)
                .unitPrice(new BigDecimal("19.99"))
                .totalPrice(new BigDecimal("99.95"))
                .createdBy("tester")
                .build();
        when(repo.save(any())).thenReturn(saved);

        OrderDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ORD-2026-001", result.getOrderNumber());
        verify(repo).save(any());
    }

    @Test
    void create_duplicateOrderNumber_throws() {
        OrderDto dto = OrderDto.builder()
                .orderNumber("ORD-2026-001")
                .userId(1L)
                .productId(1L)
                .quantity(5)
                .unitPrice(new BigDecimal("19.99"))
                .totalPrice(new BigDecimal("99.95"))
                .build();

        when(repo.existsByOrderNumber("ORD-2026-001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(repo, never()).save(any());
    }

    @Test
    void getById_found() {
        Order order = Order.builder()
                .id(5L)
                .orderNumber("ORD-2026-005")
                .userId(2L)
                .productId(2L)
                .quantity(3)
                .unitPrice(new BigDecimal("29.99"))
                .totalPrice(new BigDecimal("89.97"))
                .createdBy("user")
                .build();

        when(repo.findById(5L)).thenReturn(Optional.of(order));

        Optional<OrderDto> dto = service.getById(5L);

        assertTrue(dto.isPresent());
        assertEquals(5L, dto.get().getId());
        assertEquals("ORD-2026-005", dto.get().getOrderNumber());
    }

    @Test
    void getById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        Optional<OrderDto> dto = service.getById(99L);

        assertFalse(dto.isPresent());
    }

    @Test
    void getByOrderNumber_found() {
        Order order = Order.builder()
                .id(3L)
                .orderNumber("ORD-2026-003")
                .userId(1L)
                .productId(1L)
                .quantity(2)
                .unitPrice(new BigDecimal("15.00"))
                .totalPrice(new BigDecimal("30.00"))
                .createdBy("admin")
                .build();

        when(repo.findByOrderNumber("ORD-2026-003")).thenReturn(Optional.of(order));

        Optional<OrderDto> dto = service.getByOrderNumber("ORD-2026-003");

        assertTrue(dto.isPresent());
        assertEquals(3L, dto.get().getId());
    }

    @Test
    void list_paged() {
        Order order = Order.builder()
                .id(2L)
                .orderNumber("ORD-2026-002")
                .userId(1L)
                .productId(1L)
                .quantity(4)
                .unitPrice(new BigDecimal("25.00"))
                .totalPrice(new BigDecimal("100.00"))
                .createdBy("user")
                .build();

        Page<Order> page = new PageImpl<>(List.of(order));
        when(repo.findAll(PageRequest.of(0, 20))).thenReturn(page);

        Page<?> result = service.list(PageRequest.of(0, 20));

        assertEquals(1, result.getTotalElements());
        assertFalse(result.isEmpty());
    }

    @Test
    void update_existing() {
        Order existing = Order.builder()
                .id(3L)
                .orderNumber("ORD-2026-003")
                .userId(1L)
                .productId(1L)
                .quantity(2)
                .unitPrice(new BigDecimal("15.00"))
                .totalPrice(new BigDecimal("30.00"))
                .status("PENDING")
                .createdBy("user")
                .build();

        when(repo.findById(3L)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        OrderDto update = OrderDto.builder()
                .quantity(5)
                .status("SHIPPED")
                .build();

        OrderDto result = service.update(3L, update);

        assertEquals(5, result.getQuantity());
        assertEquals("SHIPPED", result.getStatus());
    }

    @Test
    void update_notFound_throws() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        OrderDto update = OrderDto.builder().quantity(1).build();

        assertThrows(IllegalArgumentException.class, () -> service.update(99L, update));
    }

    @Test
    void delete_delegates() {
        doNothing().when(repo).deleteById(4L);

        service.delete(4L);

        verify(repo).deleteById(4L);
    }
}

