package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderView extends JFrame {

    private JComboBox<Product> productComboBox;
    private JTextField productNameField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField sellerIDField;
    private JTextField creditCardField;
    private JTextField cvvField;
    private JButton buyButton;

    private final User currentUser;
    private final List<Product> products;
    static DataAccess dataAccess = new DataAccess();

    public OrderView(User currentUser) {
        this.currentUser = currentUser;
        this.products = dataAccess.getAllProducts();

        setTitle("Order Product");
        setSize(800, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        initListeners();

        setVisible(true);
    }

    private void initComponents() {
        // Initialize components
        JPanel mainPanel = new JPanel(new GridLayout(8, 2));
        productComboBox = new JComboBox<>(products.toArray(new Product[0]));
        productNameField = new JTextField();
        stockField = new JTextField();
        priceField = new JTextField();
        sellerIDField = new JTextField();
        creditCardField = new JTextField();
        cvvField = new JTextField();
        buyButton = new JButton("Buy");

        // Set text fields as uneditable
        productNameField.setEditable(false);
        stockField.setEditable(false);
        priceField.setEditable(false);
        sellerIDField.setEditable(false);

        // Add components to the main panel
        mainPanel.add(new JLabel("Product:"));
        mainPanel.add(productComboBox);
        mainPanel.add(new JLabel("Product Name:"));
        mainPanel.add(productNameField);
        mainPanel.add(new JLabel("Stock:"));
        mainPanel.add(stockField);
        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(priceField);
        mainPanel.add(new JLabel("Seller ID:"));
        mainPanel.add(sellerIDField);
        mainPanel.add(new JLabel("Credit Card:"));
        mainPanel.add(creditCardField);
        mainPanel.add(new JLabel("CVV:"));
        mainPanel.add(cvvField);
        mainPanel.add(buyButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initListeners() {
        // Listener for product selection
        productComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productComboBox.getSelectedItem();
                if (selectedProduct != null) {
                    productNameField.setText(selectedProduct.getProductName());
                    stockField.setText(String.valueOf(selectedProduct.getStock()));
                    priceField.setText(String.valueOf(selectedProduct.getPrice()));
                    sellerIDField.setText(String.valueOf(selectedProduct.getSellerID()));
                }
            }
        });

        // Listener for buy button
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate and process order
                String creditCard = creditCardField.getText();
                String cvv = cvvField.getText();
                if (creditCard.isEmpty() || cvv.isEmpty()) {
                    JOptionPane.showMessageDialog(OrderView.this, "Please enter credit card information.");
                } else {
                    // Create and save the order
                    Product selectedProduct = (Product) productComboBox.getSelectedItem();
                    if (selectedProduct != null) {
                        Order order = new Order(selectedProduct.getProductID(), currentUser.getUserID(), selectedProduct.getSellerID(),
                                selectedProduct.getPrice(), creditCard, cvv);
                        // Call method to save order to the database
                        saveOrder(order);
                    }
                }
            }
        });
    }

    // Method to save the order to the database (not implemented)
    private void saveOrder(Order order) {
        // Implement database saving logic here
        // dataAccess.placeOrder(order);
        System.out.println("Order saved: " + order);
        JOptionPane.showMessageDialog(OrderView.this, "Order placed successfully.");
    }

    // Main method for testing
    public static void main(String[] args) {
        // Sample user
        User currentUser = dataAccess.loadUser("rahul_2003","password");

        // Create and display the order view
        SwingUtilities.invokeLater(() -> {
            OrderView orderView = new OrderView(currentUser);
            orderView.setVisible(true);
        });
    }
}

