package com.company;

public class cannotAddNegativeQtyException extends Exception {

    private int qty;

    public cannotAddNegativeQtyException(String message, int qty) {
        super(message);
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "cannotAddNegativeQtyException{" +
                "qty=" + qty +
                '}';
    }
}
