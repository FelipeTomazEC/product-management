package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.entities.Product;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAllProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindAllProductsService sut;

    @ParameterizedTest
    @CsvSource({
            "0, 10, 20, true",
            "1, 10, 20, true",
            "0, 20, 20, false",
            "0, 25, 20, false"
    })
    @DisplayName("Page result returned by repository is parsed into response object")
    void findAllPageResponseTest(ArgumentsAccessor arguments) {
        // Given
        int pageNumber = arguments.getInteger(0);
        int pageSize = arguments.getInteger(1);
        long totalNumberOfProducts = arguments.getLong(2);
        boolean expectedHasNext = arguments.getBoolean(3);
        var products = generateMockProducts(pageSize);

        Page<Product> page = mock(Page.class);
        when(page.getTotalElements()).thenReturn(totalNumberOfProducts);
        when(page.getContent()).thenReturn(products);
        when(page.hasNext()).thenReturn(expectedHasNext);

        when(productRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(page);

        // When
        var pageRequest = PageRequest.of(pageNumber, pageSize);
        var result = sut.findAllProductInfo(pageRequest);

        // Then
        assertEquals(totalNumberOfProducts, result.totalNumberOfElements());
        assertEquals(expectedHasNext, result.hasNext());
        assertEquals(products.size(), result.products().size());
        verify(productRepository).findAll(assertArg((Pageable pageable) -> {
            assertEquals(pageSize, pageable.getPageSize());
            assertEquals(pageNumber, pageable.getPageNumber());
        }));
    }

    private List<Product> generateMockProducts(long numberOfItems) {
        return LongStream.range(0, numberOfItems)
                .mapToObj(i ->
                        Product.builder()
                                .name("Product %d".formatted(i))
                                .id(i)
                                .description("Description %d".formatted(i))
                                .price(25.30)
                                .build()
                )
                .toList();
    }
}
