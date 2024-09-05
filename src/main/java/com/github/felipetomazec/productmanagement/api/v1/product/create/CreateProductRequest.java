package com.github.felipetomazec.productmanagement.api.v1.product.create;

import lombok.Builder;

@Builder
public record CreateProductRequest(
        String name,
        String description,
        Double price
) { }
