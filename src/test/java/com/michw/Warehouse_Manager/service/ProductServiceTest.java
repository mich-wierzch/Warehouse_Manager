package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.ProductDto;
import com.michw.Warehouse_Manager.entity.Product;
import com.michw.Warehouse_Manager.entity.User;
import com.michw.Warehouse_Manager.mapper.ProductMapper;
import com.michw.Warehouse_Manager.repository.ProductRepository;
import com.michw.Warehouse_Manager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllProducts(){
        //given
        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<ProductDto> productDtos = products.stream()
                .map(productMapper::convertToDto)
                        .collect(Collectors.toList());

        when(productMapper.convertToDto(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    return productDtos.stream()
                            .filter(dto -> dto.getName().equals(product.getName()))
                            .findFirst()
                            .orElse(null);
                } );


        //when
        List<ProductDto> result = productService.findAll();

        //then
        Assertions.assertNotNull(productDtos);
        assertEquals(products.size(), productDtos.size());
    }

    @Test
    public void shouldAddNewProduct(){
        //given
        ProductDto productDto = ProductDto.builder()
                .name("TestProduct")
                .quantity(10L)
                .category("TestCategory")
                .build();

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("user@test.com");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(productRepository.findByName("TestProduct")).thenReturn(Optional.empty());
        when(productMapper.convertToDto(any(Product.class))).thenReturn(productDto);

        //when
        ResponseEntity<String> response = productService.add(productDto, principal);

        //then
        assertEquals(ResponseEntity.ok("Product added"), response);

    }


}
