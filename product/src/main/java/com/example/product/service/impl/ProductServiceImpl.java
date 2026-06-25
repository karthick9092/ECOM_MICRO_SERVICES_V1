package com.example.product.service.impl;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProductDto create(ProductDto dto) {
        if (repo.existsByProductCode(dto.getProductCode())) {
            throw new IllegalArgumentException("product_code already exists");
        }
        Product p = ProductMapper.toEntity(dto);
        Product saved = repo.save(p);
        return ProductMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> getById(Long id) {
        return repo.findById(id).map(ProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDto> getByProductCode(String productCode) {
        return repo.findByProductCode(productCode).map(ProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(ProductMapper::toDto);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product existing = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        ProductMapper.updateEntityFromDto(dto, existing);
        Product saved = repo.save(existing);
        return ProductMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}


