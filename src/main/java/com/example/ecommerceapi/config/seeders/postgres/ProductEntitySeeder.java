//package com.example.ecommerceapi.config.seeders.postgres;
//
//import com.example.ecommerceapi.model.postgres.ProductEntity;
//import com.example.ecommerceapi.service.postgres.PostgresProductService;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductEntitySeeder implements CommandLineRunner {
//
//    @Autowired
//    private PostgresProductService productService;
//
//    private Faker faker = new Faker();
//    private static final int PRODUCT_COUNT = 100000;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //if (productService.count() == 0) {
//            for (int i = 0; i < PRODUCT_COUNT; i++) {
//                ProductEntity product = new ProductEntity();
//                product.setName(faker.commerce().productName());
//                product.setPrice(Float.parseFloat(faker.commerce().price(10.0, 1000.0))); // fiyat aralığı 10-1000
//
//                productService.createProduct(product);
//            }
//            System.out.println(PRODUCT_COUNT+ " adet sahte ürün PostgreSQL'e eklendi.");
//    //    } else {
//    //        System.out.println("Ürünler zaten mevcut, ekleme yapılmadı.");
//    //    }
//    }
//}
//
