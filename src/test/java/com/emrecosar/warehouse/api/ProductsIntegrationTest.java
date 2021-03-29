package com.emrecosar.warehouse.api;

import com.emrecosar.warehouse.model.Product;
import com.emrecosar.warehouse.model.ProductArticle;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsIntegrationTest extends BaseIntegrationTest {

    @Test
    public void given_whenFetchAllProducts_thenReturnAllProducts() {

        ResponseEntity<Product[]> getProductsResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE,
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Product[].class);

        assertEquals(HttpStatus.OK, getProductsResponse.getStatusCode());
        assertTrue(getProductsResponse.getBody().length > 0);
    }

    @Test
    public void givenProduct_whenSaveAndDeleteProduct_thenValidateOperations() throws JsonProcessingException {

        // given
        Product product = createProduct(1);

        // when
        ResponseEntity postProductResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE,
                HttpMethod.POST,
                new HttpEntity<>(objectMapper.writeValueAsString(product), getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.CREATED, postProductResponse.getStatusCode());

        ResponseEntity repostProductResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE,
                HttpMethod.POST,
                new HttpEntity<>(objectMapper.writeValueAsString(product), getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.CONFLICT, repostProductResponse.getStatusCode());

        ResponseEntity<Product> getProductResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Product.class);

        assertEquals(HttpStatus.OK, getProductResponse.getStatusCode());
        assertEquals(product.getName(), getProductResponse.getBody().getName());
        assertEquals(product.getArticles().size(), getProductResponse.getBody().getArticles().size());
        assertEquals(product.getArticles().stream().mapToLong(ProductArticle::getAmount).sum(), getProductResponse.getBody().getPrice());

        // then
        ResponseEntity deleteRequestResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.DELETE,
                new HttpEntity<>(getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteRequestResponse.getStatusCode());

        ResponseEntity redeleteRequestResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.DELETE,
                new HttpEntity<>(getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, redeleteRequestResponse.getStatusCode());
    }

    @Test
    public void givenProduct_whenSellProduct_thenValidateOperations() throws JsonProcessingException {

        Product product = createProduct(1);

        ResponseEntity postProductResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE,
                HttpMethod.POST,
                new HttpEntity<>(objectMapper.writeValueAsString(product), getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.CREATED, postProductResponse.getStatusCode());

        ResponseEntity<Product> getProductBeforeSellResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Product.class);

        assertEquals(HttpStatus.OK, getProductBeforeSellResponse.getStatusCode());

        ResponseEntity sellRequestResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.POST,
                new HttpEntity<>(getAdminDefaultHeader()),
                String.class);

        ResponseEntity<Product> getProductAfterSellResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Product.class);

        assertEquals(HttpStatus.OK, getProductAfterSellResponse.getStatusCode());

        assertEquals(1, getProductBeforeSellResponse.getBody().getQuantity() - getProductAfterSellResponse.getBody().getQuantity());
    }

    @Test
    public void givenUnavailableProduct_whenSellProduct_thenReturnNotFound() {

        ResponseEntity sellRequestResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + UUID.randomUUID().toString(),
                HttpMethod.POST,
                new HttpEntity<>(getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, sellRequestResponse.getStatusCode());
    }

    @Test
    public void givenInsufficientProductArticle_whenSellProduct_thenReturnNotFound() throws JsonProcessingException {

        Product product = createProduct(100);

        ResponseEntity postProductResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE,
                HttpMethod.POST,
                new HttpEntity<>(objectMapper.writeValueAsString(product), getAdminDefaultHeader()),
                String.class);

        ResponseEntity sellRequestResponse = testRestTemplate.exchange(
                API_PRODUCT_BASE + "/" + product.getName(),
                HttpMethod.POST,
                new HttpEntity<>(getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, sellRequestResponse.getStatusCode());
    }

    // create dummy Product for testing purposes
    public Product createProduct(int numberOfArticles) {
        List<ProductArticle> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticles; i++) {
            articles.add(ProductArticle.builder((long) i + 1, 1L));
        }
        return Product.ProductBuilder(UUID.randomUUID().toString(), articles);
    }

}