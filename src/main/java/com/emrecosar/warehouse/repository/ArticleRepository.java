package com.emrecosar.warehouse.repository;

import com.emrecosar.warehouse.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(final Article entity);

    Optional<Article> findById(Long id);

    List<Article> findAll();

}
