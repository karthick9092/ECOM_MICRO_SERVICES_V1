package com.example.product.service;

import com.example.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ProductService {
    ProductDto create(ProductDto dto);
    Optional<ProductDto> getById(Long id);
    Optional<ProductDto> getByProductCode(String productCode);
    Page<ProductDto> list(Pageable pageable);
    ProductDto update(Long id, ProductDto dto);
    void delete(Long id);
}

