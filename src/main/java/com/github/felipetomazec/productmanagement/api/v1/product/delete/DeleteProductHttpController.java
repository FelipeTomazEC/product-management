package com.github.felipetomazec.productmanagement.api.v1.product.delete;

import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1;
import com.github.felipetomazec.productmanagement.services.DeleteProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = EndpointsV1.DELETE_PRODUCT)
public class DeleteProductHttpController {

    private final DeleteProductService service;

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void handle(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
