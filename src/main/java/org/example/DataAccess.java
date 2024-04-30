package org.example;

import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class DataAccess {

    private static Jedis jedis;

    public DataAccess() {
        // Initialize Jedis connection to Redis
        jedis = new Jedis("redis://default:Pv7CaXwmwQRu46d3IlWuvTVggcSZVCvp@redis-19427.c16.us-east-1-3.ec2.redns.redis-cloud.com:19427");
    }

    // Method to load user information based on username and password
    public User loadUser(String username, String password) {
        // Construct the key for the user in Redis
        String userKey = "user:" + username;

        // Check if the user exists
        if (jedis.exists(userKey)) {
            // Retrieve user information from Redis hash
            Map<String, String> userData = jedis.hgetAll(userKey);

            // Check if the provided password matches the stored password
            if (password.equals(userData.get("password"))) {
                // Create and return a User object
                return new User(Integer.parseInt(userData.get("userID")), userData.get("email"), username, password);
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }

        // Return null if user not found or password is incorrect
        return null;
    }

    // Add a product to the database
    public void addProduct(Product product) {
        try {
            // Check if the product ID already exists in the database
            if (jedis.hexists("product:" + product.getProductID(), "productName")) {
                JOptionPane.showMessageDialog(null, "Product ID already exists. Choose another.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Product ID already exists.");
                // You can show a message dialog here if needed
                return; // Exit the method without adding the product
            }
            // Store each product property as a field in a Redis hash
            jedis.hset("product:" + product.getProductID(), "productName", product.getProductName());
            jedis.hset("product:" + product.getProductID(), "stock", String.valueOf(product.getStock()));
            jedis.hset("product:" + product.getProductID(), "price", String.valueOf(product.getPrice()));
            jedis.hset("product:" + product.getProductID(), "sellerID", String.valueOf(product.getSellerID()));

            System.out.println("Product added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add product.");
        }
    }

    // Get a product from the database by productID
    public Product getProductByID(int productID) {
        try {
            // Retrieve product properties from the Redis hash
            Map<String, String> productData = jedis.hgetAll("product:" + productID);
            if (!productData.isEmpty()) {
                return new Product(
                        productID,
                        productData.get("productName"),
                        Integer.parseInt(productData.get("stock")),
                        Double.parseDouble(productData.get("price")),
                        Integer.parseInt(productData.get("sellerID"))
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get product.");
            return null;
        }
    }

    // Get all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            // Retrieve all product keys from Redis
            Set<String> keys = jedis.keys("product:*");

            // Retrieve each product from Redis and construct Product objects
            for (String key : keys) {
                Map<String, String> productData = jedis.hgetAll(key);
                if (!productData.isEmpty()) {
                    Product product = new Product(
                            Integer.parseInt(key.split(":")[1]),
                            productData.get("productName"),
                            Integer.parseInt(productData.get("stock")),
                            Double.parseDouble(productData.get("price")),
                            Integer.parseInt(productData.get("sellerID"))
                    );
                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get all products.");
        }
        return products;
    }

    // Update a product in the database
    public void updateProduct(Product product) {
        try {
            if(product.getSellerID() == Integer.parseInt(jedis.hget("product:" + product.getProductID(), "sellerID"))) {
                // Update each product property in the Redis hash
                jedis.hset("product:" + product.getProductID(), "productName", product.getProductName());
                jedis.hset("product:" + product.getProductID(), "stock", String.valueOf(product.getStock()));
                jedis.hset("product:" + product.getProductID(), "price", String.valueOf(product.getPrice()));
                jedis.hset("product:" + product.getProductID(), "sellerID", String.valueOf(product.getSellerID()));

                System.out.println("Product updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "That's not your product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to update product.");
        }
    }

    // Delete a product from the database by productID
    public void deleteProduct(Product product) {
        try {
            // Product product = getProductByID(productID);
            // Delete the product hash from Redis
            if(product.getSellerID() == Integer.parseInt(jedis.hget("product:" + product.getProductID(), "sellerID"))) {
                jedis.del("product:" + product.getProductID());
                System.out.println("Product deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "That's not your product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete product.");
        }
    }

    // Place an order in the database
    public void placeOrder(Order order) {
        try {
            // Increment the order counter and get the new order ID
            long orderID = jedis.incr("order_counter");

            // Construct the key for the order in Redis
            String orderKey = "order:" + orderID;

            // Store order details in Redis hash
            jedis.hset(orderKey, "productID", String.valueOf(order.getProductID()));
            jedis.hset(orderKey, "buyerID", String.valueOf(order.getBuyerID()));
            jedis.hset(orderKey, "sellerID", String.valueOf(order.getSellerID())); // Store sellerID
            jedis.hset(orderKey, "orderPrice", String.valueOf(order.getOrderPrice()));
            jedis.hset(orderKey, "creditCard", order.getCreditCard());
            jedis.hset(orderKey, "cvv", order.getCvv());

            // Decrement the stock of the product by 1
            int productID = order.getProductID();
            jedis.hincrBy("product:" + productID, "stock", -1);

            // store the order ID in a list for easy retrieval
            jedis.lpush("orders", String.valueOf(orderID));

            System.out.println("Order placed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to place order.");
        }
    }

    // Method to get all orders
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            // Retrieve all order keys from Redis
            Set<String> keys = jedis.keys("order:*");

            // Retrieve each order from Redis and construct Order objects
            for (String key : keys) {
                Map<String, String> orderData = jedis.hgetAll(key);
                if (!orderData.isEmpty()) {
                    Order order = new Order(
                            Integer.parseInt(orderData.get("productID")),
                            Integer.parseInt(orderData.get("buyerID")),
                            Integer.parseInt(orderData.get("sellerID")),
                            Double.parseDouble(orderData.get("orderPrice")),
                            orderData.get("creditCard"),
                            orderData.get("cvv")
                    );
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to get all orders.");
        }
        return orders;
    }

    // Method to delete an order by orderID
    public void deleteOrder(long orderID) {
        try {
            // Delete the order hash from Redis
            jedis.del("order:" + orderID);
            System.out.println("Order deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete order.");
        }
    }

    // Method to search for products within a price range
    public List<Product> searchForProductsByPrice(double minPrice, double maxPrice) {
        List<Product> productsInRange = new ArrayList<>();
        try {
            // Retrieve all product keys from Redis
            Set<String> keys = jedis.keys("product:*");

            // Retrieve each product from Redis and check if it falls within the price range
            for (String key : keys) {
                Map<String, String> productData = jedis.hgetAll(key);
                if (!productData.isEmpty()) {
                    double price = Double.parseDouble(productData.get("price"));
                    if (price >= minPrice && price <= maxPrice) {
                        Product product = new Product(
                                Integer.parseInt(key.split(":")[1]),
                                productData.get("productName"),
                                Integer.parseInt(productData.get("stock")),
                                price,
                                Integer.parseInt(productData.get("sellerID"))
                        );
                        productsInRange.add(product);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to search for products by price.");
        }
        return productsInRange;
    }


    // Close the Redis connection when done
    public void closeConnection() {
        jedis.close();
    }
}

