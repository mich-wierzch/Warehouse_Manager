package com.michw.Warehouse_Manager.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private String name;
    private Long quantity;
    private String category;
    private Long userId;
}
