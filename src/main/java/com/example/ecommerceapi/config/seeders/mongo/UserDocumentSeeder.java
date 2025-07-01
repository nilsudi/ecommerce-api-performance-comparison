//package com.example.ecommerceapi.config.seeders.mongo;
//
//import com.example.ecommerceapi.model.mongo.UserDocument;
//import com.example.ecommerceapi.model.postgres.UserEntity;
//import com.example.ecommerceapi.service.mongo.MongoUserService;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserDocumentSeeder implements CommandLineRunner {
//
//    @Autowired
//    private MongoUserService userService;
//
//    private Faker faker = new Faker();
//    private static final int USER_COUNT = 100000;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //if(userService.count() < 10) {
//        for (int i = 0; i < USER_COUNT; i++) {
//            UserDocument user = new UserDocument();
//            user.setName(faker.name().fullName());
//            user.setEmail(faker.internet().emailAddress());
//            // diğer alanlar varsa ekle
//
//            userService.createUser(user);
//        }
//        System.out.println(USER_COUNT+ " adet sahte kullanıcı MongoDB'ye eklendi.");
//        //  } else {
//        //      System.out.println("Kullanıcılar zaten mevcut, ekleme yapılmadı.");
//        //  }
//    }
//}
//
