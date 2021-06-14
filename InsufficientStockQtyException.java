package com.company;

public class InsufficientStockQtyException extends Exception {

    private Stock stock;
    private int qty;

    public InsufficientStockQtyException(String message, Stock stock, int qty) {
    super(message);

        this.stock = stock;
        this.qty = qty;
    }
}
