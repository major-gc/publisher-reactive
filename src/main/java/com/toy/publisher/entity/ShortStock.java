package com.toy.publisher.entity;

public class ShortStock {
    private String stockNum;
    private String stockName;

    public ShortStock(String stockNum, String stockName) {
        this.stockNum = stockNum;
        this.stockName = stockName;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
