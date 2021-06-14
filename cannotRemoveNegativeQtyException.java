package com.company;

public class cannotRemoveNegativeQtyException extends Exception {

   private int qty;

    public cannotRemoveNegativeQtyException(String message, int qty) {
        super(message);
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "cannotRemoveNegativeQtyException{" +
                "qty=" + qty +
                '}';
    }
}
