package com.company;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receipt {

    private final int ID;
    private final Cashier cashier;
    private LocalDateTime dateTime;
    private ArrayList<ReceiptStock> listOfStocks;
    private static int cnt = 0;

    public Receipt(Cashier cashier) {
        this.ID = ++Receipt.cnt;
        this.dateTime = LocalDateTime.now();
        this.cashier = cashier;
        this.listOfStocks = new ArrayList<>();
    }

    public void addStock(ReceiptStock receiptStock) {
        this.listOfStocks.add(receiptStock);
    }

    public BigDecimal getTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;

        for (ReceiptStock currentStock : this.listOfStocks) {
            sum = sum.add(currentStock.getSum());
        }

        return sum;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "ID=" + ID +
                ", cashier=" + cashier.getName() +
                ", dateTime=" + dateTime +
                ", listOfStocks=" + listOfStocks +
                ", total sum=" + this.getTotalSum() +
                '}';
    }
}
