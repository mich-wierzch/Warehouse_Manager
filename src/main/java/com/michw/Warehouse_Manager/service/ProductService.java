package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.entity.User;
import com.michw.Warehouse_Manager.exceptions.DuplicateProductException;
import com.michw.Warehouse_Manager.mapper.ProductMapper;
import com.michw.Warehouse_Manager.repository.ProductRepository;
import com.michw.Warehouse_Manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;


    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> add(ProductDto productRequest, Principal principal) {
        try {
            if (productRepository.findByName(productRequest.getName()).isPresent()) {
                throw new DuplicateProductException("Product is already present in the database");
            }
            User user = null;
            Optional<User> optionalUser = userRepository.findByEmail(principal.getName());
            if (optionalUser.isPresent()){
                user = optionalUser.get();
            }

            Product product = Product.builder()
                    .name(productRequest.getName())
                    .quantity(productRequest.getQuantity())
                    .category(productRequest.getCategory())
                    .user(user)
                    .build();
            productRepository.save(product);


            return ResponseEntity.ok("Product added");
        } catch (DuplicateProductException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    public ResponseEntity<String> remove(Long id){
        try {
            var product = productRepository.findById(id)
                    .orElseThrow(() -> new NullPointerException("No post with id " + id + " found"));
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted");
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
