package com.github.felipetomazec.productmanagement.services;

import com.github.felipetomazec.productmanagement.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DeleteProductService {

    private final ProductRepository repository;

    public void delete(Long id) {
        if (Objects.nonNull(id)) {
            repository.deleteById(id);
        }
    }
}
