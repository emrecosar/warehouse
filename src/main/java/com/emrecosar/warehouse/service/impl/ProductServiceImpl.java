package com.emrecosar.warehouse.service.impl;

import com.emrecosar.warehouse.exception.ConflictException;
import com.emrecosar.warehouse.exception.NotFoundException;
import com.emrecosar.warehouse.model.Product;
import com.emrecosar.warehouse.model.ProductArticle;
import com.emrecosar.warehouse.repository.ProductRepository;
import com.emrecosar.warehouse.service.ArticleService;
import com.emrecosar.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private ArticleService articleService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ArticleService articleService) {
        this.productRepository = productRepository;
        this.articleService = articleService;
    }

    public Product save(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new ConflictException(String.format("product: '%s' is already in the warehouse!", product.getName()));
        }
        product.setPrice(calculatePrice(product));
        product = productRepository.save(product);
        product.setQuantity(articleService.findMinimumSellableQuantityForAProduct(product.getArticles()));
        return product;
    }

    public List<Product> saveAll(List<Product> products) {
        return products.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    public Product get(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("product: '%s' is not found", name)));
        product.setQuantity(articleService.findMinimumSellableQuantityForAProduct(product.getArticles()));
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll()
                .stream()
                .peek(product -> product.setQuantity(articleService.findMinimumSellableQuantityForAProduct(product.getArticles())))
                .collect(Collectors.toList());
    }

    public void sell(String name) {
        // check product is exist
        Product product = productRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(String.format("product: '%s' is not found", name)));
        // check product's articles are in stock
        if (!articleService.checkArticleInventory(product.getArticles())) {
            throw new NotFoundException(String.format("Not enough stock for articles in product: %s", product.getName()));
        }
        // update article inventory
        product.getArticles().forEach(article -> articleService.reduceArticlesFromInventory(article));
    }

    public void delete(String name) {
        productRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(String.format("product: '%s' is not found", name)));
        productRepository.delete(name);
    }

    /**
     * The price of the product is the total number of containing articles
     *
     * @param product the product
     * @return {@linkplain Double} the price of the product
     */
    private Double calculatePrice(Product product) {
        return (double) product.getArticles().stream().mapToLong(ProductArticle::getAmount).sum();
    }

}
