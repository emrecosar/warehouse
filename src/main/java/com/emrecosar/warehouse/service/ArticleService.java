package com.emrecosar.warehouse.service;

import com.emrecosar.warehouse.model.Article;
import com.emrecosar.warehouse.model.ProductArticle;

import java.util.List;

/**
 * The Article Service to manage Articles
 */
public interface ArticleService {

    /**
     * Get requested article by article id
     *
     * @param id the article id
     * @return {@linkplain Article} the article instance
     */
    Article get(Long id);

    /**
     * Get all articles
     *
     * @return {@linkplain List<Article>} the articles
     */
    List<Article> getAll();

    /**
     * Save requested article
     *
     * @param article the article instance
     * @return {@linkplain Article} article
     */
    Article save(Article article);

    /**
     * Save requested articles
     *
     * @param articles the list of article instance
     * @return {@linkplain List<Article>} the list of saved article instance
     */
    List<Article> saveAll(List<Article> articles);

    /**
     * Check is all the articles with required amount are in stock
     *
     * @param productArticles the list of product article instance
     * @return {@linkplain Boolean} result of the inventory check
     */
    boolean checkArticleInventory(List<ProductArticle> productArticles);

    /**
     * Reduce the article inventory by the amount of
     *
     * @param productArticle the product article instance
     */
    void reduceArticlesFromInventory(ProductArticle productArticle);

    /**
     * Check all article stocks for given product's articles to find how many product can be sellable at the moment.
     *
     * @param articles the product's articles
     * @return {@linkplain Long} the minimum availability quantity for the given product.
     */
    Long findMinimumSellableQuantityForAProduct(List<ProductArticle> articles);
}
