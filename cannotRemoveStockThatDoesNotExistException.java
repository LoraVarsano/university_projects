package com.company;

public class cannotRemoveStockThatDoesNotExistException extends Exception {

    private Stock stock;
    private int qty;

    public cannotRemoveStockThatDoesNotExistException(String message, Stock stock, int qty) {
        super(message);
        this.qty = qty;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "cannotRemoveStockThatDoesNotExistException{" +
                "stock=" + stock +
                ", qty=" + qty +
                '}';
    }
}
