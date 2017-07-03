package com.ergun.btc.model;

public class Arbitrage {
    private Price bitstampPrice;
    private Price btcturkPrice;
    private Double tryUsd;
    private Double rawPercentage;
    private Double profit;
    private Double netProfit;

    public Arbitrage(Price bitstampPrice, Price btcturkPrice, Double tryUsd) {
        this.bitstampPrice = bitstampPrice;
        this.btcturkPrice = btcturkPrice;
        this.tryUsd = tryUsd;
    }

    public Double getTryUsd() {
        return tryUsd;
    }

    public void setTryUsd(Double tryUsd) {
        this.tryUsd = tryUsd;
    }

    public Price getBitstampPrice() {
        return bitstampPrice;
    }

    public void setBitstampPrice(Price bitstampPrice) {
        this.bitstampPrice = bitstampPrice;
    }

    public Price getBtcturkPrice() {
        return btcturkPrice;
    }

    public void setBtcturkPrice(Price btcturkPrice) {
        this.btcturkPrice = btcturkPrice;
    }

    public Double getRawPercentage() {
        return rawPercentage;
    }

    public void setRawPercentage(Double rawPercentage) {
        this.rawPercentage = rawPercentage;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    @Override
    public String toString() {
        return "Arbitrage{" +
                "bitstampPrice=" + bitstampPrice +
                ", btcturkPrice=" + btcturkPrice +
                ", tryUsd=" + tryUsd +
                ", rawPercentage=" + rawPercentage +
                ", profit=" + profit +
                ", netProfit=" + netProfit +
                '}';
    }
}
