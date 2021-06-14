package com.company;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CashRegister {

    private final int ID;
    private Cashier cashier;
    private ArrayList<ReceiptStock> listOfStocks;
    private Store parentStore;
    private Receipt currentReceipt;
    private static int cnt = 0;
    private ArrayList<Integer> listOfIssuedReceiptIDs;

    public CashRegister(Store linkedToStore) {
        this.ID = ++CashRegister.cnt;
        this.listOfIssuedReceiptIDs = new ArrayList<>();
        this.parentStore = linkedToStore;
        this.listOfStocks = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void startReceipt() {
        this.currentReceipt = new Receipt(this.cashier);
    }

    public void addStockForMarking(Stock stock, int qty) throws noSuchStockException {
        BigDecimal stockPrice = this.parentStore.getPriceOfStock(stock);

        ReceiptStock currentStock = new ReceiptStock(stock, qty, stockPrice);

        this.listOfStocks.add(currentStock);
    }

    public void markStocks() throws noSuchStockException, InsufficientStockQtyException, cannotRemoveStockThatDoesNotExistException, cannotRemoveNegativeQtyException {
        Thread thread = new Thread(new CashRegisterThread(this));
        thread.start();
    }

    public void markStockInThread() throws InsufficientStockQtyException, noSuchStockException, cannotRemoveStockThatDoesNotExistException, cannotRemoveNegativeQtyException {
        for (ReceiptStock currentStock : this.listOfStocks) {
            if (!this.parentStore.hasQtyOfStock(currentStock.getStock(), currentStock.getQty())) {
                throw new InsufficientStockQtyException(
                        "The stock qty is insufficient in this store!",
                        currentStock.getStock(),
                        currentStock.getQty());
            }

            this.currentReceipt.addStock(currentStock);
            this.parentStore.sellStock(currentStock.getStock(), currentStock.getQty());
        }
    }

    public void endReceipt() {
        this.createFile();
        this.saveReceiptToFile();
        System.out.println(this.currentReceipt);
        this.listOfIssuedReceiptIDs.add(this.currentReceipt.getID());
        this.currentReceipt = null;
        this.listOfStocks = new ArrayList<>();
    }

    public int getNumberOfReceipts() {
        return this.listOfIssuedReceiptIDs.size();
    }

    public BigDecimal turnOver() {
        BigDecimal sum = BigDecimal.ZERO;

        for (int receiptID : listOfIssuedReceiptIDs) {
            String receiptText = this.readReceipt(receiptID);

            int indexOfTotalSum = receiptText.indexOf("total sum=");

            String subStr1 = receiptText.substring(indexOfTotalSum + 10, receiptText.length() - 1);

            BigDecimal receiptSum = new BigDecimal(subStr1);

            sum = sum.add(receiptSum);

        }

        return sum;
    }

    public String readReceipt(int receiptID) {
        try {
            String fileName = "receipts/Receipt_ID" + receiptID + ".txt";
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            return str;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        }

        return "";
    }

    private void createFile() {
        try {
            File fileCreator = new File("receipts/Receipt_ID" + this.currentReceipt.getID() + ".txt");
            fileCreator.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating receipt file.");
            e.printStackTrace();
        }
    }

    private void saveReceiptToFile() {
        try {
            FileWriter receiptWriter = new FileWriter("receipts/Receipt_ID" + this.currentReceipt.getID() + ".txt");
            receiptWriter.write(this.currentReceipt.toString());
            receiptWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the receipt.");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        if(this.cashier == null) {
            return "CashRegister{" +
                    "ID=" + ID +
                    ", cashier ID= null" +
                    " null" +
                    '}';
        }
        return "CashRegister{" +
                "ID=" + ID +
                ", cashier ID=" + cashier.getID() +
                " " + cashier.getName() +
                '}';
    }
}
