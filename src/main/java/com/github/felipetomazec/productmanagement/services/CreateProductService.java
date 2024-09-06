package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.api.v1.product.create.CreateProductRequest;
import com.github.felipetomazec.productmanagement.api.v1.product.create.CreateProductResponse;
import com.github.felipetomazec.productmanagement.entities.Product;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductService {

    private final ProductRepository productRepository;

    public CreateProductResponse execute(CreateProductRequest input) {
        var product = Product.builder()
                .name(input.name().trim())
                .description(input.description().trim())
                .price(input.price())
                .build();

        product = productRepository.save(product);

        return new CreateProductResponse(product.getId());
    }
}
