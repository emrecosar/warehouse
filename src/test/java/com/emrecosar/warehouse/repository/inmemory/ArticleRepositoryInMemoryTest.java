package com.emrecosar.warehouse.repository.inmemory;

import com.emrecosar.warehouse.model.Article;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArticleRepositoryInMemoryTest {

    private ArticleRepositoryInMemory articleRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        articleRepositoryInMemory = new ArticleRepositoryInMemory();
    }

    @Test
    public void givenArticle_whenSaveArticle_thenFindById() {
        // given
        Article article = createArticle(1L);
        // pre-check
        assertFalse(articleRepositoryInMemory.findById(article.getIdentificationNumber()).isPresent());
        // when
        articleRepositoryInMemory.save(article);
        // then
        Optional<Article> actualArticle = articleRepositoryInMemory.findById(article.getIdentificationNumber());

        assertTrue(actualArticle.isPresent());
        Assertions.assertEquals(article.getIdentificationNumber(), actualArticle.get().getIdentificationNumber());
        Assertions.assertEquals(article.getName(), actualArticle.get().getName());
        Assertions.assertEquals(article.getStock(), actualArticle.get().getStock());
    }

    @Test
    public void givenArticles_whenSaveArticle_thenFindAllArticles() {
        // pre-check
        assertTrue(articleRepositoryInMemory.findAll().isEmpty());
        //given
        int articleCount = 100;
        for (int i = 0; i < articleCount; i++) {
            articleRepositoryInMemory.save(createArticle((long) i));
        }
        // when
        List<Article> actualArticles = articleRepositoryInMemory.findAll();
        // then
        Assertions.assertEquals(articleCount, actualArticles.size());
    }

    // create dummy Article for testing purposes
    public Article createArticle(Long id) {
        return Article.builder(id, UUID.randomUUID().toString(), id);
    }

}