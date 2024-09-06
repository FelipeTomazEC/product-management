package com.github.felipetomazec.productmanagement.api.v1.product.findall;

import lombok.Builder;

import java.util.List;

@Builder
public record FindAllProductInfoResponse(
        List<PartialProductInfo> products,
        long totalNumberOfElements,
        boolean hasNext
) { }
