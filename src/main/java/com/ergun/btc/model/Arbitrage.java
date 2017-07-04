package com.ergun.btc.model;

import org.springframework.data.annotation.Id;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Arbitrage {

    @Id
    private String id;

    private Price bitstampPrice;
    private Price btcturkPrice;
    private Double tryUsd;
    private Double rawPercentage;
    private Double profit;
    private Double netProfit;
    private Date date;

    public Arbitrage(){}

    public Arbitrage(Price bitstampPrice, Price btcturkPrice, Double tryUsd) {
        this.bitstampPrice = bitstampPrice;
        this.btcturkPrice = btcturkPrice;
        this.tryUsd = tryUsd;
        date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", date=" + date +
                '}';
    }

    public String prettyPrint(){
        NumberFormat formatter = new DecimalFormat("#0.00");
        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<body>");
        sb.append("<p><h3>Current Arbitrage</h3></p>");

        sb.append("<table border=1><thead><tr><td>Profit</td><td>Net Profit</td><td>Percentage</td><td>Bitstamp Price</td><td>Btcturk Price</td><td>Date</td></tr></thead>");
        sb.append("<tbody><tr>");
        sb.append("<td>").append(formatter.format(this.profit)).append(" TL </td>");
        sb.append("<td>").append(formatter.format(this.netProfit)).append(" TL </td>");
        sb.append("<td>%").append(formatter.format(this.rawPercentage)).append("</td>");
        sb.append("<td>$ ").append(this.bitstampPrice.getAmount()).append("</td>");
        sb.append("<td>").append(this.btcturkPrice.getAmount()).append(" TL </td>");
        sb.append("<td>").append(this.date).append("</td></tr></tbody></table>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
