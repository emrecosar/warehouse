package com.emrecosar.warehouse.repository.inmemory;

import com.emrecosar.warehouse.model.Article;
import com.emrecosar.warehouse.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ArticleRepositoryInMemory implements ArticleRepository {

    private final Map<Long, Article> inMemoryMap = new ConcurrentHashMap<>();

    @Override
    public Article save(final Article entity) {
        return inMemoryMap.put(entity.getIdentificationNumber(), entity);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(inMemoryMap.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(inMemoryMap.values());
    }
}
