package com.github.felipetomazec.productmanagement.api.v1.product.create;

import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1;
import com.github.felipetomazec.productmanagement.services.CreateProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = EndpointsV1.CREATE_PRODUCT)
public class CreateProductHttpController {

    private final CreateProductService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateProductResponse handle(@Valid @RequestBody CreateProductRequest request) {
        return service.execute(request);
    }
}
