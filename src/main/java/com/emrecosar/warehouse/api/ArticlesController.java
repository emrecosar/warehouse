package com.emrecosar.warehouse.api;

import com.emrecosar.warehouse.model.Article;
import com.emrecosar.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(ArticlesController.ARTICLE_BASE_PATH)
public class ArticlesController {

    public static final String ARTICLE_BASE_PATH = "/articles";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ServletContext servletContext;

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.get(id));
    }

    @PostMapping
    public ResponseEntity addArticle(@RequestBody Article article) {
        articleService.save(article);
        return ResponseEntity.created(createURI(article.getIdentificationNumber())).build();
    }

    private URI createURI(Long id) {
        return URI.create(String.format("%s%s/%s", servletContext.getContextPath(), ARTICLE_BASE_PATH, id));
    }
}
