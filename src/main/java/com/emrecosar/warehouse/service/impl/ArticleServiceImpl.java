package com.emrecosar.warehouse.service.impl;

import com.emrecosar.warehouse.exception.ConflictException;
import com.emrecosar.warehouse.exception.NotFoundException;
import com.emrecosar.warehouse.model.Article;
import com.emrecosar.warehouse.model.ProductArticle;
import com.emrecosar.warehouse.repository.ArticleRepository;
import com.emrecosar.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article get(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(String.format("Article with id: '%s' is not found", id)));
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public Article save(Article article) {
        if (articleRepository.findById(article.getIdentificationNumber()).isPresent())
            throw new ConflictException(String.format("article: '%s' is already in the warehouse!", article.getIdentificationNumber()));
        return articleRepository.save(article);
    }

    public List<Article> saveAll(List<Article> articles) {
        return articles.stream().map(article -> articleRepository.save(article)).collect(Collectors.toList());
    }

    public boolean checkArticleInventory(List<ProductArticle> productArticles) {
        return productArticles.stream()
                .allMatch(productArticle -> findById(productArticle.getArticleId()).isPresent()
                        && findById(productArticle.getArticleId()).get().getStock() >= productArticle.getAmount());
    }

    public void reduceArticlesFromInventory(ProductArticle productArticle) {
        Article article = get(productArticle.getArticleId());
        article.setStock(article.getStock() - productArticle.getAmount());
        save(article);
    }

    public Long findMinimumSellableQuantityForAProduct(List<ProductArticle> productArticles) {
        return productArticles.stream()
                .mapToLong(productArticle -> findById(productArticle.getArticleId()).map(value -> value.getStock() / productArticle.getAmount()).orElse(0L))
                .min().getAsLong();
    }

    /**
     * Find Article by it's id
     *
     * @param id the article id
     * @return {@linkplain Optional<Article>} the article
     */
    private Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

}
