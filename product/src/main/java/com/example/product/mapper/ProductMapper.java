package com.example.product.mapper;

import com.example.product.entity.Product;
import com.example.product.dto.ProductDto;

/**
 * Manual mapper implementation to avoid MapStruct/annotation-processor compatibility issues.
 */
public final class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product p) {
        if (p == null) return null;
        return ProductDto.builder()
                .id(p.getId())
                .productCode(p.getProductCode())
                .productName(p.getProductName())
                .description(p.getDescription())
                .price(p.getPrice())
                .quantity(p.getQuantity())
                .status(p.getStatus())
                .createdBy(p.getCreatedBy())
                .createdAt(p.getCreatedAt())
                .updatedBy(p.getUpdatedBy())
                .updatedAt(p.getUpdatedAt())
                .version(p.getVersion())
                .build();
    }

    public static Product toEntity(ProductDto d) {
        if (d == null) return null;
        return Product.builder()
                .id(d.getId())
                .productCode(d.getProductCode())
                .productName(d.getProductName())
                .description(d.getDescription())
                .price(d.getPrice())
                .quantity(d.getQuantity())
                .status(d.getStatus() == null ? "ACTIVE" : d.getStatus())
                .createdBy(d.getCreatedBy())
                .updatedBy(d.getUpdatedBy())
                .version(d.getVersion())
                .build();
    }

    public static void updateEntityFromDto(ProductDto d, Product p) {
        if (d == null || p == null) return;
        if (d.getProductCode() != null) p.setProductCode(d.getProductCode());
        if (d.getProductName() != null) p.setProductName(d.getProductName());
        p.setDescription(d.getDescription());
        if (d.getPrice() != null) p.setPrice(d.getPrice());
        if (d.getQuantity() != null) p.setQuantity(d.getQuantity());
        if (d.getStatus() != null) p.setStatus(d.getStatus());
        if (d.getUpdatedBy() != null) p.setUpdatedBy(d.getUpdatedBy());
    }
}


