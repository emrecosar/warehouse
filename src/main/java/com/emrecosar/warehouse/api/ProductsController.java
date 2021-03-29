package com.emrecosar.warehouse.api;

import com.emrecosar.warehouse.model.Product;
import com.emrecosar.warehouse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(ProductsController.PRODUCT_BASE_PATH)
public class ProductsController {

    public static final String PRODUCT_BASE_PATH = "/products";

    @Autowired
    private ProductService productService;

    @Autowired
    private ServletContext servletContext;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.get(name));
    }

    @PostMapping("/{name}")
    public ResponseEntity sellProduct(@PathVariable String name) {
        productService.sell(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity deleteProduct(@PathVariable String name) {
        productService.delete(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        productService.save(product);
        return ResponseEntity.created(createURI(product.getName())).build();
    }

    private URI createURI(String name) {
        return URI.create(String.format("%s%s/%s", servletContext.getContextPath(), PRODUCT_BASE_PATH, name));
    }

}
