//package com.example.ecommerceapi.config.seeders.mongo;
//
//import com.example.ecommerceapi.model.mongo.OrderDocument;
//import com.example.ecommerceapi.model.mongo.ProductDocument;
//import com.example.ecommerceapi.model.mongo.UserDocument;
//import com.example.ecommerceapi.service.mongo.MongoOrderService;
//import com.example.ecommerceapi.service.mongo.MongoProductService;
//import com.example.ecommerceapi.service.mongo.MongoUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//
//@Component
//public class OrderDocumentSeeder implements CommandLineRunner {
//
//    @Autowired
//    private MongoOrderService orderService;
//
//    @Autowired
//    private MongoUserService userService;
//
//    @Autowired
//    private MongoProductService productService;
//
//    private Random random = new Random();
//    private static final int ORDER_COUNT = 20000;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // if (orderService.count() == 0) {
//        List<UserDocument> users = userService.getAllUsers();
//        List<ProductDocument> products = productService.getAllProducts();
//
//        if (users.isEmpty() || products.isEmpty()) {
//            System.out.println("Kullanıcı veya ürün bulunamadı. Önce onları oluşturun.");
//            return;
//        }
//
//        for (int i = 0; i < ORDER_COUNT; i++) {
//            OrderDocument order = new OrderDocument();
//
//            // Rastgele kullanıcı seç
//            UserDocument user = users.get(random.nextInt(users.size()));
//            order.setUserId(user.getId());
//
//            // 1 ila 5 ürün seç
//            int productCount = 1 + random.nextInt(5);
//            Set<String> selectedProductIds = new HashSet<>();
//            float totalPrice = 0;
//
//            while (selectedProductIds.size() < productCount) {
//                ProductDocument product = products.get(random.nextInt(products.size()));
//                if (selectedProductIds.add(product.getId())) {
//                    totalPrice += product.getPrice();
//                }
//            }
//
//            order.setProductIds(new ArrayList<>(selectedProductIds));
//            order.setTotalPrice(totalPrice);
//            order.setOrderDate(new Date());
//
//            orderService.createOrder(order);
//        }
//        System.out.println(ORDER_COUNT+ " adet sahte order MongoDB'ye eklendi.");
//        // } else {
//        //     System.out.println("Mongo siparişler zaten mevcut, ekleme yapılmadı.");
//        // }
//    }
//}
//
