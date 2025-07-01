//package com.example.ecommerceapi.config.seeders.postgres;
//
//import com.example.ecommerceapi.model.postgres.UserEntity;
//import com.example.ecommerceapi.service.postgres.PostgresUserService;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserEntitySeeder implements CommandLineRunner {
//
//    @Autowired
//    private PostgresUserService userService;
//
//    private Faker faker = new Faker();
//    private static final int USER_COUNT = 100000;
//
//    @Override
//    public void run(String... args) throws Exception {
//        //if(userService.count() < 10) {
//            for (int i = 0; i < USER_COUNT; i++) {
//                UserEntity user = new UserEntity();
//                user.setName(faker.name().fullName());
//                user.setEmail(faker.internet().emailAddress());
//                // diğer alanlar varsa ekle
//
//                userService.createUser(user);
//            }
//            System.out.println(USER_COUNT+ " adet sahte kullanıcı PostgreSQL'e eklendi.");
//      //  } else {
//      //      System.out.println("Kullanıcılar zaten mevcut, ekleme yapılmadı.");
//      //  }
//    }
//}
//
