package com.github.felipetomazec.productmanagement.api.v1;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointsV1 {
    private static final String BASE_URL = "/api/v1";

    public static final String CREATE_PRODUCT = BASE_URL + "/products";
    public static final String GET_PRODUCT_BY_ID = BASE_URL + "/products/{id}";
}
