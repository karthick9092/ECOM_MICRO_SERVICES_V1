package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.impl.ProductServiceImpl;
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

class ProductServiceImplTest {

    private ProductRepository repo;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(ProductRepository.class);
        service = new ProductServiceImpl(repo);
    }

    @Test
    void create_success() {
        ProductDto dto = ProductDto.builder()
                .productCode("SKU-1")
                .productName("Name")
                .price(new BigDecimal("9.99"))
                .quantity(10)
                .build();

        when(repo.existsByProductCode("SKU-1")).thenReturn(false);
        Product saved = Product.builder()
                .id(1L)
                .productCode("SKU-1")
                .productName("Name")
                .price(new BigDecimal("9.99"))
                .quantity(10)
                .createdBy("tester")
                .build();
        when(repo.save(any())).thenReturn(saved);

        ProductDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repo).save(any());
    }

    @Test
    void create_duplicateCode_throws() {
        ProductDto dto = ProductDto.builder().productCode("SKU-1").productName("N").price(new BigDecimal("1.00")).quantity(1).build();
        when(repo.existsByProductCode("SKU-1")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        verify(repo, never()).save(any());
    }

    @Test
    void getById_found() {
        Product p = Product.builder().id(5L).productCode("C").productName("N").price(new BigDecimal("2.00")).quantity(1).createdBy("u").build();
        when(repo.findById(5L)).thenReturn(Optional.of(p));
        Optional<ProductDto> dto = service.getById(5L);
        assertTrue(dto.isPresent());
        assertEquals(5L, dto.get().getId());
    }

    @Test
    void list_paged() {
        Product p = Product.builder().id(2L).productCode("C2").productName("N2").price(new BigDecimal("3.00")).quantity(5).createdBy("u").build();
        Page<Product> page = new PageImpl<>(List.of(p));
        when(repo.findAll(PageRequest.of(0, 20))).thenReturn(page);
        Page<?> result = service.list(PageRequest.of(0, 20));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void update_existing() {
        Product existing = Product.builder().id(3L).productCode("C3").productName("Old").price(new BigDecimal("5.00")).quantity(2).createdBy("u").build();
        when(repo.findById(3L)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ProductDto update = ProductDto.builder().productName("New").price(new BigDecimal("6.00")).build();
        ProductDto result = service.update(3L, update);
        assertEquals("New", result.getProductName());
        assertEquals(new BigDecimal("6.00"), result.getPrice());
    }

    @Test
    void delete_delegates() {
        doNothing().when(repo).deleteById(4L);
        service.delete(4L);
        verify(repo).deleteById(4L);
    }
}


