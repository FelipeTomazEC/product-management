package com.github.felipetomazec.productmanagement.api.v1.product.getbyid;

import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1;
import com.github.felipetomazec.productmanagement.services.GetProductByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = EndpointsV1.GET_PRODUCT_BY_ID)
public class GetProductByIdHttpController {

    private final GetProductByIdService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ProductInfoResponse handle(@PathVariable("id") Long id) {
        return service.getProductInfo(id);
    }
}
