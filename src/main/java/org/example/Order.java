package org.example;

public class Order {
    private int productID;
    private int buyerID;
    private int sellerID; // Added sellerID field
    private double orderPrice;
    private String creditCard;
    private String cvv;

    public Order(int productID, int buyerID, int sellerID, double orderPrice, String creditCard, String cvv) {
        this.productID = productID;
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.orderPrice = orderPrice;
        this.creditCard = creditCard;
        this.cvv = cvv;
    }

    // Getters and setters for the fields
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productID=" + productID +
                ", buyerID=" + buyerID +
                ", sellerID=" + sellerID +
                ", orderPrice=" + orderPrice +
                ", creditCard='" + creditCard + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }

}

