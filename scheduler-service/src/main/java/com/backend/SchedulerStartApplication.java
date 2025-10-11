package com.backend;


import com.backend.ddd.infrastructure.config.ClientConfig;
import com.backend.ddd.infrastructure.persistence.client.CartClient;
import com.backend.ddd.infrastructure.persistence.client.OrderClient;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalCartResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderItemResponse;
import com.backend.ddd.infrastructure.persistence.client.model.ExternalOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.*;

@SpringBootApplication
@Import(ClientConfig.class)
public class SchedulerStartApplication implements CommandLineRunner {
    private static final List<UUID> USER_IDS = Arrays.asList(
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            UUID.fromString("22222222-2222-2222-2222-222222222222"),
            UUID.fromString("33333333-3333-3333-3333-333333333333"),
            UUID.fromString("44444444-4444-4444-4444-444444444444")
    );
    private static final List<UUID> PRODUCT_IDS = Arrays.asList(
            UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
            UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"),
            UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"),
            UUID.fromString("11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
            UUID.fromString("22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
            UUID.fromString("33333333-cccc-cccc-cccc-cccccccccccc")
    );
    @Autowired
    private CartClient cartClient;

    @Autowired
    private OrderClient orderClient;

    public static void main(String[] args) {
        SpringApplication.run(SchedulerStartApplication.class, args);
    }

    @Override
    public void run(String[] args) throws InterruptedException {
        Random random = new Random();
        int loopCnt = 0;

        while (true) {
            loopCnt++;
            System.out.println("Scheduler number: " + loopCnt + "\n");
            try {
                // 1. choose user in list User IDs
                UUID selectedUserId = USER_IDS.get(random.nextInt(USER_IDS.size()));
                System.out.println("Selected user: " + selectedUserId);

                // 2. choose amount of product 1-4
                int numberOfProducts = random.nextInt(4) + 1;
                System.out.println("Number of products: " + numberOfProducts);

                // 3. create a list to store cartId
                List<UUID> cartIds = new ArrayList<>();

                // 4. select random quantity of each product 1-5 than add to cart
                List<UUID> randomProductIds = new ArrayList<>(PRODUCT_IDS);
                for (int i = 0; i < numberOfProducts; i++) {
                    // select productId from rest of randomProductIds
                    UUID selectedProductId = randomProductIds.remove(random.nextInt(randomProductIds.size()));

                    // random quantity 1-5
                    int quantity = random.nextInt(5) + 1;
                    System.out.println("Adding to cart - Product ID: " + selectedProductId + ", quantity: " + quantity);

                    // call addProductToCart API
                    ExternalCartResponse cartResponse = cartClient.addProductToCart(selectedUserId, selectedProductId, quantity);
                    if (cartResponse != null && cartResponse.getId() != null) {
                        cartIds.add(cartResponse.getId());
                        System.out.println("Added cart with cart ID: " + cartResponse.getId());
                    } else {
                        System.out.println("Failed to add product to cart for Product ID: " + selectedProductId);
                    }
                }
                // 5. create order with all cartIds
                if (!cartIds.isEmpty()) {
                    ExternalOrderResponse orderResponse = orderClient.createOrder(selectedUserId, cartIds);
                    if (orderResponse != null && orderResponse.getId() != null) {
                        System.out.println("Created order successfully with order: " + orderResponse);
                    } else {
                        System.out.println("Failed to create order for user: " + selectedUserId);
                    }
                } else {
                    System.out.println("No carts to create order for user: " + selectedUserId);
                }
            } catch (Exception e) {
                System.out.println("Error in scheduler " + loopCnt + " with error: " + e.getMessage());
                throw e;
            }
            // wait for 3 seconds before next iteration
            System.out.println("Waiting for 3 seconds.\n");
            Thread.sleep(3000);
        }
    }
}