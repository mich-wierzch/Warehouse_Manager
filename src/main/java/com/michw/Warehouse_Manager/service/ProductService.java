package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public ResponseEntity<String> add(ProductDto productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .category(productRequest.getCategory())
                .build();
        productRepository.save(product);
        return ResponseEntity.ok("Product added");


    }
}
