package com.example.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
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

}