package com.company;

public class noSuchStockException extends Exception {

   private Stock stock;
    public noSuchStockException(String message, Stock stock) {
        super(message);
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "noSuchStockException{" +
                "stock=" + stock +
                '}';
    }
}
