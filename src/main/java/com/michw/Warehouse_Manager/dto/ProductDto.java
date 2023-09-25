package com.michw.Warehouse_Manager.dto;

import lombok.Builder;

@Builder
public class ProductDto {
    private String name;
    private Long quantity;
    private String category;
}
