package org.example;
public class Product {
    private int productID;
    private String productName;
    private int stock;
    private double price;
    private int sellerID;

    // Constructor
    public Product(int productID, String productName, int stock, double price, int sellerID) {
        this.productID = productID;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.sellerID = sellerID;
    }

    // Getters and setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    // toString method to display product information
    @Override
    public String toString() {
        return "Product [productID=" + productID + ", productName=" + productName + "]";
    }
}
