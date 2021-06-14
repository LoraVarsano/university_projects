package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    //For testing purposes only

    public static void main(String[] args) {

        Store store1 = new Store(BigDecimal.valueOf(200), 10, BigDecimal.valueOf(5));

        Stock stock1 = new Stock("Potato", BigDecimal.valueOf(2), StockCategory.food, LocalDate.of(2021, 07, 15));

        Stock stock2 = new Stock("Ice cream", BigDecimal.ONE, StockCategory.food, LocalDate.of(2021, 06, 10));

        Stock stock3 = new Stock("Tomato", BigDecimal.valueOf(5), StockCategory.nonFood, LocalDate.of(2025, 10, 10));

        Stock stock4 = new Stock("Cucumber", BigDecimal.valueOf(3), StockCategory.nonFood, LocalDate.of(2025, 8, 18));

        Stock stock5 = new Stock("Chicken", BigDecimal.valueOf(0.5), StockCategory.nonFood, LocalDate.of(2023, 10, 06));


        try {
            store1.addStock(stock1, 5);
            store1.addStock(stock2, 10);
            store1.addStock(stock3, 150);
            store1.addStock(stock4, 500);
            store1.addStock(stock5, 500);
        } catch (cannotAddNegativeQtyException e) {
            e.printStackTrace();
        }


        Cashier cashier1 = new Cashier("Pesho", BigDecimal.valueOf(500));
        Cashier cashier2 = new Cashier("Gosho", BigDecimal.valueOf(1500));

        store1.addCashier(cashier1);
        store1.addCashier(cashier2);

        CashRegister cashRegister1 = new CashRegister(store1);
        CashRegister cashRegister2 = new CashRegister(store1);

        store1.addCashRegister(cashRegister1);
        store1.addCashRegister(cashRegister2);

        cashRegister1.setCashier(cashier1);
        cashRegister2.setCashier(cashier2);

        cashRegister1.startReceipt();
        cashRegister2.startReceipt();

        try {
            cashRegister1.addStockForMarking(stock2, 10);
            cashRegister1.addStockForMarking(stock3, 50);
            cashRegister1.addStockForMarking(stock4, 200);
            cashRegister1.addStockForMarking(stock5, 500);

            cashRegister2.addStockForMarking(stock1, 5);
            cashRegister2.addStockForMarking(stock3, 10);
        } catch (noSuchStockException e) {
            e.printStackTrace();
        }

        try {
            cashRegister1.markStocks();
            cashRegister2.markStocks();
        } catch (noSuchStockException | InsufficientStockQtyException | cannotRemoveStockThatDoesNotExistException | cannotRemoveNegativeQtyException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            return;
        }

        cashRegister1.endReceipt();
        cashRegister2.endReceipt();

        System.out.println(store1);

        BigDecimal turnOver = store1.turnover();
        BigDecimal expenses = store1.expenses();
        BigDecimal profit = store1.profit();


        System.out.println(turnOver);
        System.out.println(expenses);
        System.out.println(profit);

        String receiptText1 = cashRegister2.readReceipt(1);
        String receiptText2 = cashRegister2.readReceipt(2);

        System.out.println(receiptText1);
        System.out.println(receiptText2);

    }
}
