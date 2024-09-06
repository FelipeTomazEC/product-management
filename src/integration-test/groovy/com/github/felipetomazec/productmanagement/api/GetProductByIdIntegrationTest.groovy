package com.github.felipetomazec.productmanagement.api

import com.github.felipetomazec.productmanagement.BaseIntegrationTest
import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1
import com.github.felipetomazec.productmanagement.api.v1.product.getbyid.ProductInfoResponse
import com.github.felipetomazec.productmanagement.entities.Product
import com.github.felipetomazec.productmanagement.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class GetProductByIdIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductRepository productRepository

    def "Product does not exist"() {
        when: "a request is performed for an nonexistent product id"
        def response = request()
                .with().pathParam("id", 3L)
                .when().get(EndpointsV1.GET_PRODUCT_BY_ID)

        then:
        response.statusCode == HttpStatus.NOT_FOUND.value()
    }

    def "Retrieve product information by id"() {
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
            .when().get(EndpointsV1.GET_PRODUCT_BY_ID)

        then:
        response.statusCode == HttpStatus.OK.value()

        and: "product information is returned"
        def responseBody = response.body().as(ProductInfoResponse)
        responseBody.price() == product.price
        responseBody.description() == product.description
        responseBody.name() == product.name
    }
}
