package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.api.v1.product.findall.FindAllProductInfoResponse;
import com.github.felipetomazec.productmanagement.api.v1.product.findall.PartialProductInfo;
import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllProductsService {

    private final ProductRepository repository;

    public FindAllProductInfoResponse findAllProductInfo(Pageable pageable) {
        var searchResult = repository.findAll(pageable);

        var productInfos = searchResult.getContent().stream()
                .map(product -> {
                    var formattedPrice = BigDecimal.valueOf(product.getPrice())
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();

                    return PartialProductInfo.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(formattedPrice)
                        .build();
                })
                .collect(Collectors.toList());

        return FindAllProductInfoResponse.builder()
                .products(productInfos)
                .hasNext(searchResult.hasNext())
                .totalNumberOfElements(searchResult.getTotalElements())
                .build();
    }
}
