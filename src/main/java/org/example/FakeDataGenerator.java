package org.example;

import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

public class FakeDataGenerator {

    public static void main(String[] args) {
        // Connect to Redis
        Jedis jedis = new Jedis("redis://default:Pv7CaXwmwQRu46d3IlWuvTVggcSZVCvp@redis-19427.c16.us-east-1-3.ec2.redns.redis-cloud.com:19427");

        // Generate fake data
        generateFakeUsers(jedis);
//        generateFakeProducts(jedis);
//        generateFakeOrders(jedis);

        // Close Redis connection
        jedis.close();
    }

    private static void generateFakeUsers(Jedis jedis) {
        // User information
        Map<String, String> user1 = new HashMap<>();
        user1.put("username", "rahul_2003");
        user1.put("password", "password");
        user1.put("email", "rahul_2003@tamu.edu");
        user1.put("userID", "1");

        // Store user information in Redis hash
        jedis.hmset("user:rahul_2003", user1);
    }

    private static void generateFakeProducts(Jedis jedis) {
        // org.example.Product information
        Map<String, String> product1 = new HashMap<>();
        product1.put("name", "org.example.Product A");
        product1.put("description", "Description of org.example.Product A");
        product1.put("price", "29.99");
        product1.put("seller_id", "1");

        Map<String, String> product2 = new HashMap<>();
        product2.put("name", "org.example.Product B");
        product2.put("description", "Description of org.example.Product B");
        product2.put("price", "19.99");
        product2.put("seller_id", "1");

        Map<String, String> product3 = new HashMap<>();
        product3.put("name", "org.example.Product C");
        product3.put("description", "Description of org.example.Product C");
        product3.put("price", "39.99");
        product3.put("seller_id", "1");

        Map<String, String> product4 = new HashMap<>();
        product4.put("name", "org.example.Product D");
        product4.put("description", "Description of org.example.Product D");
        product4.put("price", "49.99");
        product4.put("seller_id", "1");

        Map<String, String> product5 = new HashMap<>();
        product5.put("name", "org.example.Product E");
        product5.put("description", "Description of org.example.Product E");
        product5.put("price", "59.99");
        product5.put("seller_id", "1");

        // Store product information in Redis hash
        jedis.hmset("product:1", product1);
        jedis.hmset("product:2", product2);
        jedis.hmset("product:3", product3);
        jedis.hmset("product:4", product4);
        jedis.hmset("product:5", product5);

        // Add product IDs to set of available products
        jedis.sadd("available_products", "1", "2", "3", "4", "5");
    }

    private static void generateFakeOrders(Jedis jedis) {
        // Order information
        Map<String, String> order1 = new HashMap<>();
        order1.put("buyer_id", "1");
        order1.put("product_id", "3");
        order1.put("quantity", "2");
        order1.put("timestamp", "2024-04-29 10:00:00");

        Map<String, String> order2 = new HashMap<>();
        order2.put("buyer_id", "1");
        order2.put("product_id", "4");
        order2.put("quantity", "1");
        order2.put("timestamp", "2024-04-29 11:00:00");

        Map<String, String> order3 = new HashMap<>();
        order3.put("buyer_id", "1");
        order3.put("product_id", "5");
        order3.put("quantity", "3");
        order3.put("timestamp", "2024-04-29 12:00:00");

        // Store order information in Redis hash
        jedis.hmset("order:1", order1);
        jedis.hmset("order:2", order2);
        jedis.hmset("order:3", order3);

        // Add order IDs to list of orders
        jedis.lpush("orders", "1", "2", "3");
    }
}
