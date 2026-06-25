package com.example.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Order transfer object")
public class OrderDto {

    @Schema(description = "Primary key", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Unique order number", example = "ORD-2026-001")
    private String orderNumber;

    @NotNull
    @Schema(description = "User ID", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @NotNull
    @Min(1)
    @Schema(description = "Quantity ordered", example = "5")
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01")
    @Schema(description = "Unit price", example = "19.99")
    private BigDecimal unitPrice;

    @NotNull
    @DecimalMin(value = "0.01")
    @Schema(description = "Total price", example = "99.95")
    private BigDecimal totalPrice;

    @Schema(description = "Order status", example = "PENDING")
    private String status;

    @Schema(description = "Shipping address", example = "123 Main St, City")
    private String shippingAddress;

    @Schema(description = "Order date (auto-set on creation)", example = "2026-06-15T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime orderDate;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Created by", example = "admin")
    private String createdBy;

    @Schema(description = "Creation timestamp", example = "2026-06-15T12:34:56", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Size(max = 100)
    @Schema(description = "Last updated by", example = "editor", accessMode = Schema.AccessMode.READ_ONLY)
    private String updatedBy;

    @Schema(description = "Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "Optimistic lock version", accessMode = Schema.AccessMode.READ_ONLY)
    private Long version;
}

