package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ProductView extends JFrame {

    private JTextField productIDField;
    private JTextField productNameField;
    private JTextField stockField;
    private JTextField priceField;
    private JTextField sellerIDField;
    private final JComboBox<String> operationComboBox;
    private final JPanel panel = new JPanel();
    static DataAccess dataAccess = new DataAccess();
    private final User currentUser;


    public ProductView(User currentUser) {
        this.currentUser = currentUser;
        setTitle("Product View");
        setSize(300, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dataAccess.closeConnection(); // Call closeConnection when window is closed
            }
        });

        panel.setLayout(new GridLayout(6, 2));

        JLabel operationLabel = new JLabel("Select Operation:");
        String[] operations = {"Show Products", "Add Product Listing", "Update Product Listing", "Delete Product Listing"};
        operationComboBox = new JComboBox<>(operations);
        panel.add(operationLabel);
        panel.add(operationComboBox);

        operationComboBox.addActionListener(e -> {
            String selectedOperation = (String) operationComboBox.getSelectedItem();
            assert selectedOperation != null;
            switch (selectedOperation) {
                case "Show Products":
                    showProducts();
                    break;
                case "Add Product Listing":
                    addProduct();
                    break;
                case "Update Product Listing":
                    updateProduct();
                    break;
                case "Delete Product Listing":
                    deleteProduct();
                    break;
            }
        });

        add(panel);
    }

    private void showProducts() {
        panel.removeAll();
        setSize(800, 400);
        panel.setLayout(new BorderLayout());

        List<Product> productList = dataAccess.getAllProducts();

        String[] columnNames = {"Product ID", "Product Name", "Stock", "Price", "Seller ID"};
        Object[][] data = new Object[productList.size()][5];

        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            data[i][0] = product.getProductID();
            data[i][1] = product.getProductName();
            data[i][2] = product.getStock();
            data[i][3] = product.getPrice();
            data[i][4] = product.getSellerID();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = getBackButton();
        panel.add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void addProduct() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(6, 2));

        JLabel productIDLabel = new JLabel("Product ID:");
        productIDField = new JTextField();
        panel.add(productIDLabel);
        panel.add(productIDField);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();
        panel.add(productNameLabel);
        panel.add(productNameField);

        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField();
        panel.add(stockLabel);
        panel.add(stockField);

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        panel.add(priceLabel);
        panel.add(priceField);

        JLabel sellerIDLabel = new JLabel("Seller ID:");
        sellerIDField = new JTextField();
        sellerIDField.setText(String.valueOf(currentUser.getUserID()));
        sellerIDField.setEditable(false);
        panel.add(sellerIDLabel);
        panel.add(sellerIDField);

        JButton addButton = getAddButton();
        panel.add(addButton);

        JButton backButton = getBackButton();
        panel.add(backButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private JButton getAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // Add product
            int productID = Integer.parseInt(productIDField.getText());
            String productName = productNameField.getText();
            int stock = Integer.parseInt(stockField.getText());
            double price = Double.parseDouble(priceField.getText());
            int sellerID = Integer.parseInt(sellerIDField.getText());
            Product product = new Product(productID, productName, stock, price, sellerID);
            dataAccess.addProduct(product);
            // Show operation selection view
            showOperationSelectionView();
        });
        return addButton;
    }

    private void updateProduct() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(6, 2));

        JLabel productIDLabel = new JLabel("Product ID:");
        productIDField = new JTextField();
        panel.add(productIDLabel);
        panel.add(productIDField);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();
        panel.add(productNameLabel);
        panel.add(productNameField);

        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField();
        panel.add(stockLabel);
        panel.add(stockField);

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        panel.add(priceLabel);
        panel.add(priceField);

        JLabel sellerIDLabel = new JLabel("Seller ID:");
        sellerIDField = new JTextField();
        sellerIDField.setText(String.valueOf(currentUser.getUserID()));
        sellerIDField.setEditable(false);
        panel.add(sellerIDLabel);
        panel.add(sellerIDField);

        JButton updateButton = getUpdateButton();
        panel.add(updateButton);

        JButton backButton = getBackButton();
        panel.add(backButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private JButton getUpdateButton() {
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            // Update product
            int productID = Integer.parseInt(productIDField.getText());
            String productName = productNameField.getText();
            int stock = Integer.parseInt(stockField.getText());
            double price = Double.parseDouble(priceField.getText());
            int sellerID = Integer.parseInt(sellerIDField.getText());
            Product product = new Product(productID, productName, stock, price, sellerID);
            dataAccess.updateProduct(product);
            // Show operation selection view
            showOperationSelectionView();
        });
        return updateButton;
    }

    private void deleteProduct() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(2, 2));

        JLabel productIDLabel = new JLabel("Product ID:");
        productIDField = new JTextField();
        panel.add(productIDLabel);
        panel.add(productIDField);

        JButton removeButton = getRemoveButton();
        panel.add(removeButton);

        JButton backButton = getBackButton();
        panel.add(backButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private JButton getRemoveButton() {
        JButton removeButton = new JButton("Delete");
        removeButton.addActionListener(e -> {
            // Remove product
            int productID = Integer.parseInt(productIDField.getText());
            Product product = dataAccess.getProductByID(productID);
            product.setSellerID(Integer.parseInt(String.valueOf(currentUser.getUserID())));
            dataAccess.deleteProduct(product);
            // Show operation selection view
            showOperationSelectionView();
        });
        return removeButton;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            // Show operation selection view
            showOperationSelectionView();
        });
        return backButton;
    }

    private void showOperationSelectionView() {
        panel.removeAll();
        setSize(300, 250);
        panel.setLayout(new GridLayout(6, 2));
        JLabel operationLabel = new JLabel("Select Operation:");
        panel.add(operationLabel);
        panel.add(operationComboBox);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        // Sample user for debugging
        User currentUser = dataAccess.loadUser("rahul_2003","password");
        SwingUtilities.invokeLater(() -> {
            ProductView productView = new ProductView(currentUser); // Open Product View
            productView.setVisible(true);
        });
    }
}
