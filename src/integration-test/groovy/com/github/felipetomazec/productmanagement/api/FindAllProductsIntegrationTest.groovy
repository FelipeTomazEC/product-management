package com.github.felipetomazec.productmanagement.api

import com.github.felipetomazec.productmanagement.BaseIntegrationTest
import com.github.felipetomazec.productmanagement.api.v1.EndpointsV1
import com.github.felipetomazec.productmanagement.api.v1.product.findall.FindAllProductInfoResponse
import com.github.felipetomazec.productmanagement.entities.Product
import com.github.felipetomazec.productmanagement.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import java.util.concurrent.ThreadLocalRandom
import java.util.stream.LongStream

class FindAllProductsIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductRepository productRepository

    ThreadLocalRandom random = ThreadLocalRandom.current()

    def cleanup() {
        productRepository.deleteAll()
    }

    def "Retrieve all products"() {
        given: "there are products registered in the db"
        def products = LongStream.range(0, 20)
            .mapToObj(i -> Product.builder()
                    .name("Product ${i}")
                    .description("Description of product ${i}")
                    .price(random.nextDouble(1000))
                    .build()
            ).sorted ((p1, p2) -> p1.name <=> p2.name)
             .toList()

        productRepository.saveAll(products)

        and: "page size is < total number of products"
        def pageSize = 10

        when: "first page is requested"
        def pageOneResponse = request()
                .with().queryParam("size", pageSize)
                .with().queryParam("page", 0)
                .when().get(EndpointsV1.FIND_ALL_PRODUCTS)

        then:
        pageOneResponse.statusCode == HttpStatus.OK.value()

        and: "page one is correctly build"
        def pageOneResponseBody = pageOneResponse.body().as(FindAllProductInfoResponse)
        pageOneResponseBody.hasNext()
        pageOneResponseBody.totalNumberOfElements() == products.size()
        pageOneResponseBody.products().stream().allMatch(productInfo -> {
            def correspondingProduct = products.subList(0, pageSize)
                    .stream()
                    .filter(p -> p.id == productInfo.id())
                    .findFirst()
                    .get()

            return productInfo.name() == correspondingProduct.name
        })

        when: "second page is requested"
        def pageTwoResponse = request()
                .with().queryParam("size", pageSize)
                .with().queryParam("page", 1)
                .when().get(EndpointsV1.FIND_ALL_PRODUCTS)

        then: "page two is correctly build"
        def pageTwoResponseBody = pageTwoResponse.body().as(FindAllProductInfoResponse)
        !pageTwoResponseBody.hasNext()
        pageTwoResponseBody.totalNumberOfElements() == products.size()
        pageTwoResponseBody.products().stream().allMatch(productInfo -> {
            def correspondingProduct = products.subList(pageSize, products.size())
                    .stream()
                    .filter(p -> p.id == productInfo.id())
                    .findFirst()
                    .get()

            return productInfo.name() == correspondingProduct.name
        })

        when: "third page is requested"
        def pageThreeResponse = request()
                .with().queryParam("size", pageSize)
                .with().queryParam("page", 2)
                .when().get(EndpointsV1.FIND_ALL_PRODUCTS)

        then: "page three is correctly build"
        def pageThreeResponseBody = pageThreeResponse.body().as(FindAllProductInfoResponse)
        !pageThreeResponseBody.hasNext()
        pageThreeResponseBody.totalNumberOfElements() == products.size()
        pageThreeResponseBody.products().isEmpty()
    }
}
