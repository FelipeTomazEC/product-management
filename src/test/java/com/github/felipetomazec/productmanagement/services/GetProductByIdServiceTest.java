package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.entities.Product;
import com.github.felipetomazec.productmanagement.exceptions.ProductNotFoundException;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductByIdServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdService sut;

    @Test
    @DisplayName("Product does not exist")
    void productDoesNotExistTest() {
        // Given
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            //When
            sut.getProductInfo(10L);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "50.906, 50.91",
            "50.904, 50.90",
            "50.99, 50.99"
    })
    @DisplayName("Get product information for the given id")
    void retrieveProductInformation(double price, double expectedReturnPrice) {
        // Given
        var product = Product.builder()
                .name("Some product name")
                .description("Some product description")
                .price(price)
                .id(2L)
                .build();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // When
        var info = sut.getProductInfo(product.getId());

        // Then price has 2 decimal places of precision
        assertEquals(expectedReturnPrice, info.price());

        // And
        assertEquals(product.getName(), info.name());
        assertEquals(product.getDescription(), info.description());
    }
}
