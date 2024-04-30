package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static spark.Spark.*;

public class MainWindow extends JFrame {
    User activeUser;
    private static boolean sparkServerStarted = false;
    static DataAccess dataAccess = new DataAccess();

    public MainWindow(User newUser) {
        activeUser = newUser;
        setTitle("Main Window - " + activeUser.getUsername());
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("Online Shopping System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set a large font
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

        // Buttons to open different views
        JButton openProductViewButton = new JButton("List Products");
        openProductViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                org.example.ProductView productView = new ProductView(activeUser); // Open Job View
                productView.setVisible(true);
            }
        });

        JButton openOrderViewButton = new JButton("Buy Products");
        openOrderViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView orderView = new OrderView(activeUser); // Open Employee View
                orderView.setVisible(true);
            }
        });

        JButton openSearchProductsViewButton = new JButton("Search Products");
        openSearchProductsViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchProducts searchProducts = new SearchProducts();
                searchProducts.setVisible(true);
            }
        });

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Logging out...");
                // Hide the current window
                setVisible(false);
                // Open a new login screen
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });

        // Panel to hold the title and buttons
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout for positioning
        panel.add(titleLabel, BorderLayout.NORTH); // Add the title label at the top
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel to hold buttons
        buttonPanel.add(openProductViewButton);
        buttonPanel.add(openOrderViewButton);
        buttonPanel.add(openSearchProductsViewButton);
        panel.add(buttonPanel, BorderLayout.CENTER); // Add the buttons below the title
        panel.add(logoutButton, BorderLayout.SOUTH); // Add the logout button at the bottom

        add(panel);

        // Start the Spark server if it hasn't been started yet
        if (!isSparkServerStarted()) {
            startSparkServer();
            sparkServerStarted = true; // Update the flag
        }
    }

    // Method to start the Spark server
    private void startSparkServer() {
        port(8080); // Set the port number

        // Define a route for handling product search requests
        get("/product/search", (request, response) -> {
            String minPriceStr = request.queryParams("minprice");
            String maxPriceStr = request.queryParams("maxprice");

            // Convert the query parameters to double
            double minPrice = Double.parseDouble(minPriceStr);
            double maxPrice = Double.parseDouble(maxPriceStr);

            // Perform the search using the data access class
            List<Product> productsInRange = dataAccess.searchForProductsByPrice(minPrice, maxPrice);

            // Construct a string containing information about each product
            StringBuilder result = new StringBuilder();
            result.append("<html><body><h1>Products within Specified Price Range!</h1><ul>");
            for (Product product : productsInRange) {
                result.append("Product ID: ").append(product.getProductID()).append("<br>");
                result.append("Product Name: ").append(product.getProductName()).append("<br>");
                result.append("Quantity Left in Stock: ").append(product.getStock()).append("<br>");
                result.append("Price: ").append(product.getPrice()).append("<br>");
                result.append("Seller ID: ").append(product.getSellerID()).append("<br>");
                result.append("<br>");
            }
            result.append("</ul></body></html>");

            // Set response type to HTML
            response.type("text/html");

            // Return the search results as HTML
            return result.toString();
            // For test purposes, let's just return a simple message
            // return "Searching products with min price: " + minPrice + ", max price: " + maxPrice;
        });
    }

    // Method to check if the Spark server is already started
    private boolean isSparkServerStarted() {
        return sparkServerStarted;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen(); // get Login Screen when application starts
            loginScreen.setVisible(true);
//            MainWindow mainWindow = new MainWindow(null);
//            mainWindow.setVisible(true);
        });
    }
}