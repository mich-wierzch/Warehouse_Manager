package com.michw.Warehouse_Manager.controller;

import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<Product> findProducts(){
        return productService.findAll();
    }

}
