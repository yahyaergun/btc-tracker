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
    private Price koinimPrice;
    private Price paribuPrice;
    private Double tryUsd;
    private Double btcturkProfit;
    private Double koinimProfit;
    private Double paribuProfit;
    private Date date;

    public Arbitrage(){}

    public Arbitrage(Price bitstampPrice, Price btcturkPrice, Price koinimPrice,Price paribuPrice,Double tryUsd) {
        this.bitstampPrice = bitstampPrice;
        this.btcturkPrice = btcturkPrice;
        this.koinimPrice = koinimPrice;
        this.paribuPrice = paribuPrice;
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

    public Double getBtcturkProfit() {
        return btcturkProfit;
    }

    public void setBtcturkProfit(Double btcturkProfit) {
        this.btcturkProfit = btcturkProfit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Price getKoinimPrice() {
        return koinimPrice;
    }

    public void setKoinimPrice(Price koinimPrice) {
        this.koinimPrice = koinimPrice;
    }

    public Price getParibuPrice() {
        return paribuPrice;
    }

    public void setParibuPrice(Price paribuPrice) {
        this.paribuPrice = paribuPrice;
    }

    public Double getKoinimProfit() {
        return koinimProfit;
    }

    public void setKoinimProfit(Double koinimProfit) {
        this.koinimProfit = koinimProfit;
    }

    public Double getParibuProfit() {
        return paribuProfit;
    }

    public void setParibuProfit(Double paribuProfit) {
        this.paribuProfit = paribuProfit;
    }

    public String prettyPrint(){
        NumberFormat formatter = new DecimalFormat("#0.00");
        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>btc tracker</title></head>\n");
        sb.append("<body>");
        sb.append("<p><h3>Current Arbitrage</h3></p>");

        sb.append("<table border=1><thead style=\"font-weight:bold;\"><tr><td>BtcTurk Profit</td>" +
                "<td>Koinim Profit</td><td>Paribu Profit</td><td>Bitstamp Price</td><td>Btcturk Price</td>" +
                "<td>Koinim Price</td><td>Paribu Price</td><td>Dolar/TL</td><td>Date</td></tr></thead>");
        sb.append("<tbody><tr>");
        sb.append("<td>").append(formatter.format(this.btcturkProfit)).append(" ₺</td>");
        sb.append("<td>").append(formatter.format(this.koinimProfit)).append(" ₺</td>");
        sb.append("<td>").append(formatter.format(this.paribuProfit)).append(" ₺</td>");
        sb.append("<td>$").append(this.bitstampPrice.getAmount()).append("</td>");
        sb.append("<td>").append(this.btcturkPrice.getAmount()).append(" ₺</td>");
        sb.append("<td>").append(this.koinimPrice.getAmount()).append(" ₺</td>");
        sb.append("<td>").append(this.paribuPrice.getAmount()).append(" ₺</td>");
        sb.append("<td>").append(this.tryUsd).append("</td>");
        sb.append("<td>").append(this.date).append("</td></tr></tbody></table>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
