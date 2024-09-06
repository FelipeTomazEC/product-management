package com.github.felipetomazec.productmanagement.api.v1.product.findall;

import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1;
import com.github.felipetomazec.productmanagement.services.FindAllProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = EndpointsV1.FIND_ALL_PRODUCTS)
public class FindAllProductInfoHttpController {

    private final FindAllProductsService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public FindAllProductInfoResponse handle(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return service.findAllProductInfo(pageable);
    }
}
