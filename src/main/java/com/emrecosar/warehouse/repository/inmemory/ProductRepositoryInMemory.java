package com.emrecosar.warehouse.repository.inmemory;

import com.emrecosar.warehouse.model.Product;
import com.emrecosar.warehouse.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepositoryInMemory implements ProductRepository {

    private final Map<String, Product> inMemoryMap = new ConcurrentHashMap<>();

    @Override
    public Product save(Product entity) {
        inMemoryMap.put(entity.getName(), entity);
        return entity;
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(inMemoryMap.get(name));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(inMemoryMap.values());
    }

    @Override
    public void delete(String name) {
        inMemoryMap.remove(name);
    }
}
