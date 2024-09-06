package com.github.felipetomazec.productmanagement.api

import com.github.felipetomazec.productmanagement.BaseIntegrationTest
import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1
import com.github.felipetomazec.productmanagement.entities.Product
import com.github.felipetomazec.productmanagement.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class DeleteProductIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductRepository productRepository

    def "Product does not exist"() {
        when: "a request is performed for an nonexistent product id"
        def response = request()
                .with().pathParam("id", 3L)
                .when().delete(EndpointsV1.DELETE_PRODUCT)

        then:
        response.statusCode == HttpStatus.NO_CONTENT.value()
    }

    def "Delete a product that exists"() {
        given: "a product exists in database"
        def product = Product.builder()
            .name("Echo dot")
            .description("Amazon Virtual Assistant")
            .price(245.99)
            .build()
        product = productRepository.save(product)

        when: "a request is made using the product id"
        def response = request()
            .with().pathParam("id", product.getId())
            .when().delete(EndpointsV1.DELETE_PRODUCT)

        then:
        response.statusCode == HttpStatus.NO_CONTENT.value()

        and: "product information is deleted from database"
        !productRepository.existsById(product.getId())
    }
}
