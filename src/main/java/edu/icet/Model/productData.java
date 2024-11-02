package edu.icet.Model;

import java.math.BigDecimal;

public class productData {
    private final String productId;
    private final String brand;
    private final String productName;
    private final String status;
    private final BigDecimal price;


    public productData(String productId, String brand, String productName, String status, BigDecimal price) {
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
        this.status = status;
        this.price = price;
    }


    public String getProductId() {
        return productId;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductName() {
        return productName;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
