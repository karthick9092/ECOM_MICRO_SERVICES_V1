package com.example.product.controller;

import com.example.product.config.AppConfig;
import com.example.product.dto.ProductDto;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ProductController.
 * Tests the controller logic with mocked service (no Spring framework overhead).
 */
class ProductControllerTest {


    @Test
    void getById_returnsProduct() {
        ProductService service = Mockito.mock(ProductService.class);
        AppConfig appConfig = Mockito.mock(AppConfig.class);
        ProductController controller = new ProductController(service, appConfig);

        ProductDto product = ProductDto.builder()
                .id(5L)
                .productCode("SKU-005")
                .productName("Another Product")
                .price(new BigDecimal("49.99"))
                .quantity(20)
                .build();

        when(service.getById(5L)).thenReturn(Optional.of(product));

        var response = controller.getById(5L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void getById_returnsNotFoundWhenNotExists() {
        ProductService service = Mockito.mock(ProductService.class);
        AppConfig appConfig = Mockito.mock(AppConfig.class);
        ProductController controller = new ProductController(service, appConfig);

        when(service.getById(99L)).thenReturn(Optional.empty());

        var response = controller.getById(99L);

        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(404, response.getStatusCode().value());
    }
}

