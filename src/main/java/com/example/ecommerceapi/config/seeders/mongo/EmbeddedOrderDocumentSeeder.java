//package com.example.ecommerceapi.config.seeders.mongo;
//
//import com.example.ecommerceapi.model.mongo.ProductDocument;
//import com.example.ecommerceapi.model.mongo.UserDocument;
//import com.example.ecommerceapi.service.mongo.MongoEmbeddedOrderService;
//import com.example.ecommerceapi.service.mongo.MongoProductService;
//import com.example.ecommerceapi.service.mongo.MongoUserService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//
//@Component
//public class EmbeddedOrderDocumentSeeder {
//
//    @Autowired
//    private MongoEmbeddedOrderService orderService;
//
//    @Autowired
//    private MongoUserService userService;
//
//    @Autowired
//    private MongoProductService productService;
//
//    private final Random random = new Random();
//    private static final int ORDER_COUNT = 100000;
//
//    @PostConstruct
//    public void seedEmbeddedOrders() {
//
//        List<UserDocument> allUsers     = userService.getAllUsers();
//        List<ProductDocument> allProducts = productService.getAllProducts();
//
//        if (allUsers.isEmpty() || allProducts.isEmpty()) {
//            System.out.println("⚠️  Kullanıcı veya ürün verisi bulunamadı. Önce bunları seedleyin.");
//            return;
//        }
//
//        for (int i = 0; i < ORDER_COUNT; i++) {
//
//            // 1. Rastgele kullanıcı
//            UserDocument user = allUsers.get(random.nextInt(allUsers.size()));
//
//            // 2. 1-5 arası benzersiz ürün ID’si seç
//            int productCount = 1 + random.nextInt(5);
//            List<String> productIds = random.ints(0, allProducts.size())
//                    .distinct()
//                    .limit(productCount)
//                    .mapToObj(idx -> allProducts.get(idx).getId())
//                    .collect(Collectors.toList());
//
//            // 3. Servisi çağır
//            orderService.createEmbeddedOrder(user.getId(), productIds);
//
//            if ((i + 1) % 50_000 == 0) {
//                System.out.printf("%,d embedded order eklendi… %n", i + 1);
//            }
//        }
//
//        System.out.printf("%,d adet sahte embedded order MongoDB'ye eklendi.%n", ORDER_COUNT);
//    }
//}
