package com.emrecosar.warehouse.model.wrapper;

import com.emrecosar.warehouse.model.Product;

import java.util.List;

public class ProductsWrapper {

    public List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
