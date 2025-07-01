//package com.example.ecommerceapi.config.seeders.mongo;
//
//import com.example.ecommerceapi.model.mongo.ProductDocument;
//import com.example.ecommerceapi.model.postgres.ProductEntity;
//import com.example.ecommerceapi.service.mongo.MongoProductService;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductDocumentSeeder implements CommandLineRunner {
//
//    @Autowired
//    private MongoProductService productService;
//
//    private Faker faker = new Faker();
//    private static final int PRODUCT_COUNT = 100000;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //if (productService.count() == 0) {
//        for (int i = 0; i < PRODUCT_COUNT; i++) {
//            ProductDocument product = new ProductDocument();
//            product.setName(faker.commerce().productName());
//            product.setPrice(Float.parseFloat(faker.commerce().price(10.0, 1000.0))); // fiyat aralığı 10-1000
//
//            productService.createProduct(product);
//        }
//        System.out.println(PRODUCT_COUNT+ " adet sahte ürün MongoDB'ye eklendi.");
//        //    } else {
//        //        System.out.println("Ürünler zaten mevcut, ekleme yapılmadı.");
//        //    }
//    }
//}
//
