package com.michw.Warehouse_Manager.mapper;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto convertToDto(Product product){
        return ProductDto.builder()
                .name(product.getName())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .userId(product.getUser().getId())
                .build();
    }
}
