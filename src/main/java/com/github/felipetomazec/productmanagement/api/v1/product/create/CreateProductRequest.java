package com.github.felipetomazec.productmanagement.api.v1.product.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateProductRequest(
        @NotBlank(message = "A product must have a name")
        String name,

        @NotBlank(message = "A product must have a description")
        String description,

        @Min(value = 0, message = "price cannot be a negative value")
        @NotNull(message = "A product must have a price")
        Double price
) { }
