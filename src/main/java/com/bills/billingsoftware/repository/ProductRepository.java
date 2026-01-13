package com.bills.billingsoftware.repository;
import java.util.*;
import com.bills.billingsoftware.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final List<Product> products = new ArrayList<>();

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void save(Product product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
        products.add(product);
    }
}
