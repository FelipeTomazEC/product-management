package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.api.v1.product.getbyid.ProductInfoResponse;
import com.github.felipetomazec.productmanagement.exceptions.ProductNotFoundException;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class GetProductByIdService {

    private final ProductRepository repository;

    public ProductInfoResponse getProductInfo(Long productId) {
        var product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        var priceWith2Decimals = BigDecimal.valueOf(product.getPrice())
                .setScale(2, RoundingMode.HALF_UP);

        return ProductInfoResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(priceWith2Decimals.doubleValue())
                .build();
    }
}
