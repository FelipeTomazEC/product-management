package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductService sut;

    @Test
    @DisplayName("Delete a product of a given id")
    void deleteProductById() {
        // Given
        Long productId = 1L;

        // When
        sut.delete(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);  // Verify the method was called exactly once
    }

    @Test
    @DisplayName("Does not call repository for null ids")
    void deleteProductWithNullId() {
        // When
        assertDoesNotThrow(() -> sut.delete(null));

        // Then
        verify(productRepository, never()).deleteById(any());
    }
}
