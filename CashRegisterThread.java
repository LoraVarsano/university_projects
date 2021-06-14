package com.company;

public class CashRegisterThread implements Runnable {

    private CashRegister cashRegister;

    public CashRegisterThread(CashRegister cashRegister) {
        this.cashRegister = cashRegister;
    }

    public void run() {
        try {
            this.cashRegister.markStockInThread();
        } catch (InsufficientStockQtyException | cannotRemoveNegativeQtyException | cannotRemoveStockThatDoesNotExistException | noSuchStockException e) {
            e.printStackTrace();
        }
    }
}
