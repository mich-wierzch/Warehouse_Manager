package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.exceptions.DuplicateProductException;
import com.michw.Warehouse_Manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public ResponseEntity<String> add(ProductDto productRequest) {
        try {
            if (productRepository.findByName(productRequest.getName()).isPresent()) {
                throw new DuplicateProductException("Product is already present in the database");
            }
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .quantity(productRequest.getQuantity())
                    .category(productRequest.getCategory())
                    .build();
            productRepository.save(product);
            return ResponseEntity.ok("Product added");
        } catch (DuplicateProductException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    public ResponseEntity<String> remove(Long id){
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }

    public ResponseEntity<String> update(Long id, ProductDto request){
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("No post found..."));

            product.setName(Optional.ofNullable(request.getName()).orElse(product.getName()));
            product.setQuantity(Optional.ofNullable(request.getQuantity()).orElse(product.getQuantity()));
            product.setCategory(Optional.ofNullable(request.getCategory()).orElse(product.getCategory()));

            productRepository.save(product);
            return ResponseEntity.ok("Product updated");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
