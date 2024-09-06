package com.github.felipetomazec.productmanagement.api.v1.product.findall;

import lombok.Builder;

@Builder
public record PartialProductInfo(
        Long id,
        String name,
        Double price
) { }
