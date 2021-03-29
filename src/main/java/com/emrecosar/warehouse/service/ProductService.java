package com.emrecosar.warehouse.service;

import com.emrecosar.warehouse.model.Product;

import java.util.List;

/**
 * the Product Service to manage products
 */
public interface ProductService {

    /**
     * Get all products
     *
     * @return {@linkplain List<Product>} list of products
     */
    List<Product> getAll();

    /**
     * Get a product by it's name
     *
     * @return {@linkplain Product} the product
     */
    Product get(String name);

    /**
     * Save all products
     *
     * @param products the list of products
     * @return {@linkplain List<Product>} list of saved products
     */
    List<Product> saveAll(List<Product> products);

    /**
     * Save a product
     *
     * @param product the product
     * @return {@linkplain Product} the product
     */
    Product save(Product product);

    /**
     * Sell a product from inventory by checking availability with related articles
     *
     * @param name the product name
     */
    void sell(String name);

    /**
     * Delete a product
     *
     * @param name the product name
     */
    void delete(String name);
}
