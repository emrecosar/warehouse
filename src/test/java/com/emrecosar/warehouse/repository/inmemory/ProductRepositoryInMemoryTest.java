package com.emrecosar.warehouse.repository.inmemory;

import com.emrecosar.warehouse.model.Product;
import com.emrecosar.warehouse.model.ProductArticle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRepositoryInMemoryTest {

    private ProductRepositoryInMemory productRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        productRepositoryInMemory = new ProductRepositoryInMemory();
    }

    @Test
    public void givenProduct_whenSaveProduct_thenFindByName() {
        // given
        Product product = createProduct(1);
        // pre-check
        assertFalse(productRepositoryInMemory.findByName(product.getName()).isPresent());
        // when
        productRepositoryInMemory.save(product);
        // then
        Optional<Product> actualProduct = productRepositoryInMemory.findByName(product.getName());

        assertTrue(actualProduct.isPresent());
        Assertions.assertEquals(product.getArticles().size(), actualProduct.get().getArticles().size());
        Assertions.assertEquals(product.getName(), actualProduct.get().getName());
    }

    @Test
    public void givenProducts_whenSaveAndDeleteProducts_thenValidate() {
        // pre-check
        assertTrue(productRepositoryInMemory.findAll().isEmpty());
        //given
        int articleCount = 100;
        for (int i = 0; i < articleCount; i++) {
            productRepositoryInMemory.save(createProduct(i));
        }
        // when
        List<Product> actualProducts = productRepositoryInMemory.findAll();
        // then
        Assertions.assertEquals(articleCount, actualProducts.size());

        // when
        for (int i = 0; i < actualProducts.size(); i++) {
            productRepositoryInMemory.delete(actualProducts.get(i).getName());
        }
        actualProducts = productRepositoryInMemory.findAll();
        // then
        Assertions.assertTrue(actualProducts.isEmpty());
    }

    // create dummy Product for testing purposes
    public Product createProduct(int numberOfArticles) {
        List<ProductArticle> articles = new ArrayList<>();
        for (int i = 0; i < numberOfArticles; i++) {
            articles.add(ProductArticle.builder((long) i, (long) i));
        }
        return Product.ProductBuilder(UUID.randomUUID().toString(), articles);
    }

}