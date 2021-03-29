package com.emrecosar.warehouse.repository;

import com.emrecosar.warehouse.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByName(String name);

    List<Product> findAll();

    void delete(String name);
}
