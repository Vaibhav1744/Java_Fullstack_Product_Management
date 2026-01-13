package com.bills.billingsoftware.controller;

import com.bills.billingsoftware.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    // CREATE product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        return product;
    }

    // READ all products
    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    // READ product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        products.removeIf(p -> p.getId() == id);
        return "Product deleted successfully";
    }
}