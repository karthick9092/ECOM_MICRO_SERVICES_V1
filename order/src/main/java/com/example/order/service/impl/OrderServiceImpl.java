package com.example.order.service.impl;

import com.example.order.dto.OrderDto;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;

    public OrderServiceImpl(OrderRepository repo) {
        this.repo = repo;
    }

    @Override
    public OrderDto create(OrderDto dto) {
        if (repo.existsByOrderNumber(dto.getOrderNumber())) {
            throw new IllegalArgumentException("order_number already exists");
        }
        Order order = OrderMapper.toEntity(dto);
        Order saved = repo.save(order);
        return OrderMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDto> getById(Long id) {
        return repo.findById(id).map(OrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDto> getByOrderNumber(String orderNumber) {
        return repo.findByOrderNumber(orderNumber).map(OrderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(OrderMapper::toDto);
    }

    @Override
    public OrderDto update(Long id, OrderDto dto) {
        Order existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        OrderMapper.updateEntityFromDto(dto, existing);
        Order saved = repo.save(existing);
        return OrderMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}

