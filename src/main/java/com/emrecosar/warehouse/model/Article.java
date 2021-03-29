package com.emrecosar.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {

    @JsonProperty("art_id")
    private Long identificationNumber;

    private String name;

    private Long stock;

    public static Article builder(Long identificationNumber, String name, Long stock) {
        Article article = new Article();
        article.setIdentificationNumber(identificationNumber);
        article.setName(name);
        article.setStock(stock);
        return article;
    }

    public Long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
