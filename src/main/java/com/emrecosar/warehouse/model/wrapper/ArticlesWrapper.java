package com.emrecosar.warehouse.model.wrapper;

import com.emrecosar.warehouse.model.Article;

import java.util.List;

public class ArticlesWrapper {

    public List<Article> inventory;

    public List<Article> getInventory() {
        return inventory;
    }

    public void setInventory(List<Article> inventory) {
        this.inventory = inventory;
    }
}
