package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Stock {

    private final int ID;
    private String name;
    private BigDecimal deliveryPrice;
    private StockCategory category;
    private LocalDate expirationDate;

    private static int cnt = 0;

    public Stock(String name, BigDecimal deliveryPrice, StockCategory category, LocalDate expirationDate) {
        this.ID = ++Stock.cnt;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.category = category;
        this.expirationDate = expirationDate;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public StockCategory getCategory() {
        return category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", category=" + category +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
