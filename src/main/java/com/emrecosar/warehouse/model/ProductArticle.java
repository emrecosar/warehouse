package com.emrecosar.warehouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductArticle {

    @JsonProperty("art_id")
    private Long articleId;

    @JsonProperty("amount_of")
    private Long amount;

    public static ProductArticle builder(Long articleId, Long amount) {
        ProductArticle productArticle = new ProductArticle();
        productArticle.setArticleId(articleId);
        productArticle.setAmount(amount);
        return productArticle;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
