package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cashier {
    private String name;
    private final int ID;
    private BigDecimal salary;

    private static int cnt = 0;

    public Cashier(String name, BigDecimal salary) {
        this.ID = ++Cashier.cnt;
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", salary=" + salary +
                '}';
    }
}
