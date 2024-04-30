package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SearchProducts extends JFrame {
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton searchButton;
    private static final DataAccess dataAccess = new DataAccess();

    public SearchProducts() {
        setTitle("Search Products");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        initListeners();

        setVisible(true);
    }

    private void initComponents() {
        // Initialize components
        JPanel mainPanel = new JPanel(new GridLayout(3, 2));
        minPriceField = new JTextField();
        maxPriceField = new JTextField();
        searchButton = new JButton("Search");

        // Add components to the main panel
        mainPanel.add(new JLabel("Minimum Price:"));
        mainPanel.add(minPriceField);
        mainPanel.add(new JLabel("Maximum Price:"));
        mainPanel.add(maxPriceField);
        mainPanel.add(searchButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void initListeners() {
        // Listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get minimum and maximum prices from text fields
                String minPriceText = minPriceField.getText();
                String maxPriceText = maxPriceField.getText();

                try {
                    // Construct URL with query parameters
                    String url = "http://localhost:8080/product/search?minprice=" + minPriceText + "&maxprice=" + maxPriceText;
                    openWebpage(url);
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(SearchProducts.this, "Failed to open webpage.");
                }
            }
        });
    }

    // Method to open a webpage in the default browser
    private void openWebpage(String url) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

    // Main method for testing
    public static void main(String[] args) {
        // Create and display the SearchProducts view
        SwingUtilities.invokeLater(SearchProducts::new);
    }
}

