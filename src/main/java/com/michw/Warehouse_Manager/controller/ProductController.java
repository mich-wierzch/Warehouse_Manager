package com.michw.Warehouse_Manager.controller;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<Product> getProducts(){
        return productService.findAll();
    }
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productRequest){
        return productService.add(productRequest);
    }

}
