package com.example.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product transfer object")
public class ProductDto {

	@Schema(description = "Primary key", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Schema(description = "Unique product code", example = "SKU-001")
	private String productCode;

	@NotBlank
	@Size(max = 255)
	@Schema(description = "Product name", example = "Widget")
	private String productName;

	@Schema(description = "Product description")
	private String description;

	@NotNull
	@DecimalMin(value = "0.00", inclusive = true)
	@Schema(description = "Product price", example = "19.99")
	private BigDecimal price;

	@NotNull
	@Min(0)
	@Schema(description = "Available quantity", example = "100")
	private Integer quantity;

	@Schema(description = "Status", example = "ACTIVE")
	private String status;

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
