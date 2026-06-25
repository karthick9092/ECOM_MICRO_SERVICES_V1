package com.example.order.service;

import com.example.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface OrderService {
    OrderDto create(OrderDto dto);
    Optional<OrderDto> getById(Long id);
    Optional<OrderDto> getByOrderNumber(String orderNumber);
    Page<OrderDto> list(Pageable pageable);
    OrderDto update(Long id, OrderDto dto);
    void delete(Long id);
}

