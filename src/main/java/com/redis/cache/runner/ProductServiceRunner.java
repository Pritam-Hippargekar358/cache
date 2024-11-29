package com.redis.cache.runner;

import com.redis.cache.dto.Product;
import com.redis.cache.enums.ProductStatus;
import com.redis.cache.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductServiceRunner implements CommandLineRunner {
    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        MDC.put("userId", "1");
        Product product1 = new Product();
        product1.setName("Product 3");
        product1.setPrice(300.0);
        product1.setStatus(ProductStatus.IN_ACTIVE);
//        productService.insert(product1);
        boolean deleted = productService.delete(2L);
        System.out.println("Fetching Product with ID 3: " + productService.get(3L).orElse(null));

        try{
            throw new RuntimeException("Explicit exception");
        }catch(Exception ex ){
            log.error("Exception while : {}",ex.getMessage(),ex);
        }

log.info("Finished excecution: {}", MDC.get("userId"));
        MDC.remove("userId");
    }
}
