package com.emrecosar.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Product {

    private String name;

    private Double price;

    private Long quantity;

    @JsonProperty("contain_articles")
    private List<ProductArticle> articles;

    public static Product ProductBuilder(String name, List<ProductArticle> articles) {
        Product product = new Product();
        product.setName(name);
        product.setArticles(articles);
        return product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public List<ProductArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<ProductArticle> articles) {
        this.articles = articles;
    }
}
