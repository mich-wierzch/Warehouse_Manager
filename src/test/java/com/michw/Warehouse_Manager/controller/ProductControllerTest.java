package com.michw.Warehouse_Manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.Collections;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    public void testGetProducts() throws Exception {
        when(productService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(productService, times(1)).findAll();
        verifyNoMoreInteractions(productService);
    }
    @Test
    @WithMockUser
    public void testAddProduct() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("TestProduct")
                .quantity(3L)
                .category("TestCategory")
                .build();

        when(productService.add(any(ProductDto.class), any(Principal.class))).thenReturn(ResponseEntity.ok("Product added successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product added successfully"));

        verify(productService, times(1)).add(any(ProductDto.class), any(Principal.class));
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser
    public void testRemoveProduct() throws Exception {
        Long productId = 1L;

        when(productService.remove(productId)).thenReturn(ResponseEntity.ok("Product removed successfully"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product removed successfully"));

        verify(productService, times(1)).remove(productId);
        verifyNoMoreInteractions(productService);
    }

    @Test
    @WithMockUser
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDto productDto = ProductDto.builder()
                .name("TestProduct")
                .quantity(3L)
                .category("TestCategory")
                .build();

        when(productService.update(eq(productId), any(ProductDto.class)))
                .thenReturn(ResponseEntity.ok("Product updated successfully"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product updated successfully"));

        verify(productService, times(1)).update(eq(productId), any(ProductDto.class));
        verifyNoMoreInteractions(productService);
    }


}
