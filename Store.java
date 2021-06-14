package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class Store {

    private BigDecimal percentageMarkup;
    private int saleDaysUntilGoneBad;
    private BigDecimal salePercentageUntilGoneBad;
    private BigDecimal totalSum;

    private ArrayList<Cashier> listOfCashiers;
    private Map<Integer, Stock> stocksMapping;
    private Map<Integer, Integer> stockQtyMapping;
    private Map<Integer, Integer> soldStockQtyMapping;
    private ArrayList<CashRegister> listOfCashRegisters;

    public Store(BigDecimal percentageMarkup, int saleDaysUntilGoneBad, BigDecimal salePercentageUntilGoneBad) {
        this.percentageMarkup = percentageMarkup;
        this.saleDaysUntilGoneBad = saleDaysUntilGoneBad;
        this.salePercentageUntilGoneBad = salePercentageUntilGoneBad;
        this.listOfCashiers = new ArrayList<>();
        this.listOfCashRegisters = new ArrayList<>();
        this.stockQtyMapping = new HashMap<>();
        this.soldStockQtyMapping = new HashMap<>();
        this.stocksMapping = new HashMap<>();
    }

    public void addCashier(Cashier newCashier) {
        this.listOfCashiers.add(newCashier);
    }

    public void addCashRegister(CashRegister newCashRegister) {
        this.listOfCashRegisters.add(newCashRegister);
    }

    public void addStock(Stock stock, int qty) throws cannotAddNegativeQtyException {
        if (qty <= 0) {
            throw new cannotAddNegativeQtyException("Cannot add negative or zero stocks!", qty);
        }

        if (this.stocksMapping.containsKey(stock.getID())) {
            int currentQty = this.stockQtyMapping.get(stock.getID());

            this.stockQtyMapping.put(stock.getID(), currentQty + qty);
        }
        else {
            this.stocksMapping.put(stock.getID(), stock);
            this.stockQtyMapping.put(stock.getID(), qty);
        }
    }

    public void removeStock(Stock stock, int qty) throws cannotRemoveNegativeQtyException, cannotRemoveStockThatDoesNotExistException, InsufficientStockQtyException {
        if (qty <= 0) {
            throw new cannotRemoveNegativeQtyException("Cannot remove negative qty!", qty);
        }

        if(!this.hasQtyOfStock(stock, qty)) {
            throw new cannotRemoveStockThatDoesNotExistException("Cannot remove stock that does not exist!", stock, qty);
        }

        int currentQty = this.stockQtyMapping.get(stock.getID());

        int newQty = currentQty - qty;
        this.stockQtyMapping.put(stock.getID(), newQty);
    }

    public synchronized void sellStock(Stock stock, int qty) throws cannotRemoveNegativeQtyException, cannotRemoveStockThatDoesNotExistException, InsufficientStockQtyException {
        this.removeStock(stock, qty);

        if (soldStockQtyMapping.containsKey(stock.getID())) {
            int currentSoldStocks = soldStockQtyMapping.get(stock.getID());
            int newQty = currentSoldStocks + qty;
            this.soldStockQtyMapping.put(stock.getID(), newQty);
        }
        else {
            this.soldStockQtyMapping.put(stock.getID(), qty);
        }
    }

    public synchronized boolean hasQtyOfStock(Stock stock, int qty) {
        if (!this.stockQtyMapping.containsKey(stock.getID())) {
            return false;
        }

        int currentQty = this.stockQtyMapping.get(stock.getID());

        if (currentQty >= qty) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal getPriceOfStock(Stock stock) throws noSuchStockException {

        if (!this.stocksMapping.containsKey(stock.getID())) {
            throw new noSuchStockException("No such stock", stock);
        }

        BigDecimal sellPrice = stock.getDeliveryPrice().
                multiply(this.percentageMarkup.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE));

        if (stock.getCategory() == StockCategory.nonFood) {
            return sellPrice;
        }
        else {
            LocalDate today = LocalDate.now();
            long daysBetween = DAYS.between(today, stock.getExpirationDate());

            if(daysBetween <= this.saleDaysUntilGoneBad) {
                BigDecimal discountPrice = sellPrice.multiply(BigDecimal.ONE.
                        subtract(this.salePercentageUntilGoneBad.divide(BigDecimal.valueOf(100))));

                return discountPrice;
            }

            return sellPrice;
        }

    }

    public BigDecimal turnover() {
        BigDecimal sum = BigDecimal.ZERO;
        for (CashRegister currentCashRegister: this.listOfCashRegisters) {
            sum = sum.add(currentCashRegister.turnOver());
        }
        return sum;
    }

    public BigDecimal expenses() {
        BigDecimal sum = BigDecimal.ZERO;

        for(Stock currentStock : this.stocksMapping.values()) {
            BigDecimal currentStockDeliveryPrice = currentStock.getDeliveryPrice();

            int currentStockID = currentStock.getID();

            int qtyInStore = this.stockQtyMapping.get(currentStockID);

            int soldQty = 0;

            if (this.soldStockQtyMapping.containsKey(currentStockID)) {
                soldQty = this.soldStockQtyMapping.get(currentStockID);
            }

            int totalQty = qtyInStore + soldQty;

            sum = sum.add(currentStockDeliveryPrice.multiply(BigDecimal.valueOf(totalQty)));
        }

        for (Cashier currentCashier: this.listOfCashiers) {
            sum = sum.add(currentCashier.getSalary());
        }

        return sum;
    }

    public BigDecimal profit() {
        return this.turnover().subtract(this.expenses());
    }

    @Override
    public String toString() {
        return "Store {" +
                "percentageMarkup=" + percentageMarkup +
                ", saleDaysUntilGoneBad=" + saleDaysUntilGoneBad +
                ", salePercentageUntilGoneBad=" + salePercentageUntilGoneBad +
                ", totalSum=" + totalSum +
                ", listOfCashiers=" + listOfCashiers +
                ", stocksMapping=" + stocksMapping +
                ", stockQtyMapping=" + stockQtyMapping +
                ", soldStockQtyMapping=" + soldStockQtyMapping +
                ", listOfCashRegisters=" + listOfCashRegisters +
                '}';
    }
}
