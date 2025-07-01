//package com.example.ecommerceapi.config.seeders.postgres;
//
//import com.example.ecommerceapi.model.postgres.ProductEntity;
//import com.example.ecommerceapi.model.postgres.UserEntity;
//import com.example.ecommerceapi.service.postgres.PostgresOrderService;
//import com.example.ecommerceapi.service.postgres.PostgresProductService;
//import com.example.ecommerceapi.service.postgres.PostgresUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Component
//public class OrderEntitySeeder implements CommandLineRunner {
//
//    @Autowired
//    private PostgresOrderService orderService;
//
//    @Autowired
//    private PostgresUserService userService;
//
//    @Autowired
//    private PostgresProductService productService;
//
//    private final Random random = new Random();
//    private static final int ORDER_COUNT = 100000;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        List<UserEntity> users    = userService.getAllUsers();
//        List<ProductEntity> products = productService.getAllProducts();
//
//        if (users.isEmpty() || products.isEmpty()) {
//            System.out.println("Kullanıcı veya ürün bulunamadı. Önce onları oluşturun.");
//            return;
//        }
//
//        for (int i = 0; i < ORDER_COUNT; i++) {
//
//            // 1. Rastgele kullanıcı
//            UserEntity user = users.get(random.nextInt(users.size()));
//
//            // 2. 1-5 arası rastgele ürün ID'lerini topla
//            int productCount = 1 + random.nextInt(5);

//            List<Long> productIds = random.ints(0, products.size())
//                    .distinct()
//                    .limit(productCount)
//                    .mapToObj(idx -> products.get(idx).getId())
//                    .collect(Collectors.toList());
//
//            // 3. Servis
//            orderService.createOrder(user, productIds);
//
//        }
//
//        System.out.printf("%,d adet sahte order PostgreSQL'e eklendi.%n", ORDER_COUNT);
//    }
//}
