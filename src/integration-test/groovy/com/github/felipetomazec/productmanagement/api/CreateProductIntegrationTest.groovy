package com.github.felipetomazec.productmanagement.api

import com.github.felipetomazec.productmanagement.BaseIntegrationTest
import com.github.felipetomazec.productmanagement.api.v1.product.create.CreateProductRequest
import com.github.felipetomazec.productmanagement.api.v1.product.create.CreateProductResponse
import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1
import com.github.felipetomazec.productmanagement.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import spock.lang.Unroll

class CreateProductIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductRepository repository

    @Unroll
    def "Missing #missingParam - BAD REQUEST"() {
        given: "request body is missing a required parameter"
        def requestBody = CreateProductRequest.builder()
                .description(description)
                .price(price)
                .name(name)
                .build()

        when: "a request is performed using that request body"
        def response = request()
                .with().body(requestBody)
                .when().post(EndpointsV1.CREATE_PRODUCT)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST.value()

        where:
        missingParam  | name          | description   | price
        "name"        | "   "         | "Computer"    | 13000.5
        "name"        | null          | "Macbook Pro" | 13000.5
        "description" | "Macbook Pro" | null          | 13000.5
        "description" | "Macbook Pro" | ""            | 13000.5
        "price"       | "Macbook Pro" | "Computer"    | null
    }

    def "Negative price - BAD REQUEST"() {
        given: "price is a negative number"
        def requestBody = CreateProductRequest.builder()
                .name("Mouse Logitech")
                .description("USB mouse with additional control buttons")
                .price(-0.15)
                .build()

        when:
        def response = request()
                .with().body(requestBody)
                .when().post(EndpointsV1.CREATE_PRODUCT)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST.value()
    }

    def "Create product successfully"() {
        given: "request body has necessary params"
        def requestBody = CreateProductRequest.builder()
                .name("Galaxy S21")
                .description("Smartphone Samsung")
                .price(2000.99)
                .build()

        when: "a request is performed using that request body"
        def response = request()
                .with().body(requestBody)
                .when().post(EndpointsV1.CREATE_PRODUCT)

        then:
        response.statusCode == HttpStatus.CREATED.value()

        and: "new product id is returned"
        def responseBody = response.body().as(CreateProductResponse)
        Objects.nonNull(responseBody.id())

        and: "product is saved in the database"
        repository.findById(responseBody.id()).isPresent()
    }
}
