package com.emrecosar.warehouse.api;

import com.emrecosar.warehouse.model.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticlesIntegrationTest extends BaseIntegrationTest {

    @Test
    public void givenArticle_whenSaveAndGetArticle_thenValidateOperations() throws JsonProcessingException {

        Article article = createArticle(1L);

        assertEquals(HttpStatus.NOT_FOUND, testRestTemplate.exchange(
                API_ARTICLE_BASE + "/" + article.getIdentificationNumber(),
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                String.class).getStatusCode());

        ResponseEntity postArticleResponse = testRestTemplate.exchange(
                API_ARTICLE_BASE,
                HttpMethod.POST,
                new HttpEntity<>(objectMapper.writeValueAsString(article), getAdminDefaultHeader()),
                String.class);

        assertEquals(HttpStatus.CREATED, postArticleResponse.getStatusCode());

        ResponseEntity<Article> getArticleResponse = testRestTemplate.exchange(
                API_ARTICLE_BASE + "/" + article.getIdentificationNumber(),
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Article.class);

        assertEquals(HttpStatus.OK, getArticleResponse.getStatusCode());
        assertEquals(article.getIdentificationNumber(), getArticleResponse.getBody().getIdentificationNumber());
        assertEquals(article.getName(), getArticleResponse.getBody().getName());
        assertEquals(article.getStock(), getArticleResponse.getBody().getStock());
    }

    @Test
    public void givenArticles_whenSaveAndGetArticles_thenValidateOperations() throws JsonProcessingException {

        ResponseEntity<Article[]> getArticlesBeforeResponse = testRestTemplate.exchange(
                API_ARTICLE_BASE,
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Article[].class);

        int articleCount = 10;
        for (int i = 1; i <= articleCount; i++) {
            Article article = createArticle((long) i);

            ResponseEntity postProductResponse = testRestTemplate.exchange(
                    API_ARTICLE_BASE,
                    HttpMethod.POST,
                    new HttpEntity<>(objectMapper.writeValueAsString(article), getAdminDefaultHeader()),
                    String.class);

            assertEquals(HttpStatus.CREATED, postProductResponse.getStatusCode());
        }

        ResponseEntity<Article[]> getArticlesAfterResponse = testRestTemplate.exchange(
                API_ARTICLE_BASE,
                HttpMethod.GET,
                new HttpEntity<>(getUserDefaultHeader()),
                Article[].class);

        assertEquals(HttpStatus.OK, getArticlesAfterResponse.getStatusCode());
        assertEquals(articleCount, getArticlesAfterResponse.getBody().length - getArticlesBeforeResponse.getBody().length);
    }

    // create dummy Article for testing purposes
    public Article createArticle(Long id) {
        return Article.builder(id + 1000, UUID.randomUUID().toString(), id);
    }

}