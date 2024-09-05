package com.github.felipetomazec.productmanagement.api.v1.product.create;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointsV1 {
    private static final String BASE_URL = "/api/v1";

    public static final String CREATE_PRODUCT = BASE_URL + "/products";
}
