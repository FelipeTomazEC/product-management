package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.api.v1.product.create.CreateProductRequest;
import com.github.felipetomazec.productmanagement.entities.Product;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductService sut;

    @Test
    @DisplayName("Product is saved to database")
    void createProductTest() {
        // Given
        var input = CreateProductRequest.builder()
                .name("     Name")
                .description("    Description    ")
                .price(35.0)
                .build();

        var newProductId = 1000L;
        when(productRepository.save(any(Product.class))).thenAnswer((Answer<Product>) invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(newProductId);
            return product;
        });

        //When
        var result = sut.createProduct(input);

        // Then
        assertEquals(newProductId, result.id());

        verify(productRepository).save(assertArg(product -> {
            assertEquals("Description", product.getDescription());
            assertEquals("Name", product.getName());
            assertEquals(35.0, product.getPrice());
        }));
    }
}
