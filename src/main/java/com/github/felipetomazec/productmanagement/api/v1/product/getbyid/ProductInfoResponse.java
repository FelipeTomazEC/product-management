package com.github.felipetomazec.productmanagement.api.v1.product.getbyid;

import lombok.Builder;

@Builder
public record ProductInfoResponse(
        String name,
        String description,
        Double price
) { }
