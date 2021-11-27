package com.muhammad.warehouse.model.DTO;

import com.muhammad.warehouse.model.Stock;

import java.util.List;

public class OrderDto {
    private long cashierId;
    private List<Stock> stocks;

    public OrderDto(long cashierId, List<Stock> stocks) {
        this.cashierId = cashierId;
        this.stocks = stocks;
    }

    public long getCashierId() {
        return cashierId;
    }

    public void setCashierId(long cashierId) {
        this.cashierId = cashierId;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
