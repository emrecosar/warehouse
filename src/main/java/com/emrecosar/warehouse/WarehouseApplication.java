package com.emrecosar.warehouse;

import com.emrecosar.warehouse.model.wrapper.ArticlesWrapper;
import com.emrecosar.warehouse.model.wrapper.ProductsWrapper;
import com.emrecosar.warehouse.service.ArticleService;
import com.emrecosar.warehouse.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@SpringBootApplication
public class WarehouseApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArticleService articleService;

    @Autowired
    ProductService productService;

    public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseApplication.class);

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    public void run(String... args) {
        this.loadDataFromJson("inventory",
                ArticlesWrapper.class,
                objectMapper,
                articlesWrapper -> articleService.saveAll(articlesWrapper.getInventory()));
        this.loadDataFromJson("products",
                ProductsWrapper.class,
                objectMapper,
                ProductsWrapper -> productService.saveAll(ProductsWrapper.getProducts()));
    }

    private <T> void loadDataFromJson(String filename,
                                      Class<T> clazz,
                                      ObjectMapper mapper,
                                      Consumer<T> function) {
        final InputStream inputStream = TypeReference.class
                .getResourceAsStream(String.format("/json/%s.json",
                        filename));
        try {
            final T entities = mapper.readValue(inputStream, clazz);
            function.accept(entities);
            LOGGER.info("file: '{}' is loaded", filename);
        } catch (final IOException e) {
            LOGGER.error("Unable to load file: '{}'", filename, e);
        }
    }
}
