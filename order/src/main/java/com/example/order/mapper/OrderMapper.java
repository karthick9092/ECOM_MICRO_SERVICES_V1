package com.example.order.mapper;

import com.example.order.dto.OrderDto;
import com.example.order.entity.Order;

/**
 * Manual mapper implementation to avoid MapStruct/annotation-processor compatibility issues.
 */
public final class OrderMapper {

    private OrderMapper() {}

    public static OrderDto toDto(Order e) {
        if (e == null) return null;
        return OrderDto.builder()
                .id(e.getId())
                .orderNumber(e.getOrderNumber())
                .userId(e.getUserId())
                .productId(e.getProductId())
                .quantity(e.getQuantity())
                .unitPrice(e.getUnitPrice())
                .totalPrice(e.getTotalPrice())
                .status(e.getStatus())
                .shippingAddress(e.getShippingAddress())
                .orderDate(e.getOrderDate())
                .createdBy(e.getCreatedBy())
                .createdAt(e.getCreatedAt())
                .updatedBy(e.getUpdatedBy())
                .updatedAt(e.getUpdatedAt())
                .version(e.getVersion())
                .build();
    }

    public static Order toEntity(OrderDto d) {
        if (d == null) return null;
        return Order.builder()
                .id(d.getId())
                .orderNumber(d.getOrderNumber())
                .userId(d.getUserId())
                .productId(d.getProductId())
                .quantity(d.getQuantity())
                .unitPrice(d.getUnitPrice())
                .totalPrice(d.getTotalPrice())
                .status(d.getStatus() == null ? "PENDING" : d.getStatus())
                .shippingAddress(d.getShippingAddress())
                .createdBy(d.getCreatedBy())
                .updatedBy(d.getUpdatedBy())
                .version(d.getVersion())
                .build();
    }

    public static void updateEntityFromDto(OrderDto dto, Order entity) {
        if (dto.getOrderNumber() != null) {
            entity.setOrderNumber(dto.getOrderNumber());
        }
        if (dto.getQuantity() != null) {
            entity.setQuantity(dto.getQuantity());
        }
        if (dto.getUnitPrice() != null) {
            entity.setUnitPrice(dto.getUnitPrice());
        }
        if (dto.getTotalPrice() != null) {
            entity.setTotalPrice(dto.getTotalPrice());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getShippingAddress() != null) {
            entity.setShippingAddress(dto.getShippingAddress());
        }
        if (dto.getUpdatedBy() != null) {
            entity.setUpdatedBy(dto.getUpdatedBy());
        }
    }
}

