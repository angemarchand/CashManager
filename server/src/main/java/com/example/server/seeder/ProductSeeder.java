package com.example.server.seeder;


import com.example.server.entity.Product;
import com.example.server.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductSeeder implements CommandLineRunner {
    ProductRepository productRepository;

    public ProductSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadProductData();
    }

    private void loadProductData() {
        if(productRepository.count() == 0){
            Product product1 = new Product("courgette",BigDecimal.valueOf(2.0) ,"example.com",100);
            Product product2 = new Product("salade",BigDecimal.valueOf(2.0) ,"example.com",50);
            Product product3 = new Product("tomate cérise",BigDecimal.valueOf(2.0) ,"example.com",70);
            Product product4 = new Product("panais",BigDecimal.valueOf(2.0) ,"example.com",200);
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
        }
    }
}