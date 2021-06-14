package com.company;

import java.math.BigDecimal;

public class ReceiptStock {

    private Stock stock;
    private int qty;
    private BigDecimal price;

    public ReceiptStock(Stock stock, int qty, BigDecimal price) {
        this.stock = stock;
        this.qty = qty;
        this.price = price;
    }

    public BigDecimal getSum() {
        return this.price.multiply(BigDecimal.valueOf(this.qty));
    }

    public int getQty() {
        return this.qty;
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getName() {
        return this.stock.getName();
    }

    @Override
    public String toString() {
        return '{' +
                "name=" + stock.getName() +
                ", qty=" + qty +
                ", price=" + price +
                ", sum=" + this.getSum() +
                '}';
    }
}
