package com.ergun.btc.service.impl;

import com.ergun.btc.Constants;
import com.ergun.btc.dao.ArbitrageDao;
import com.ergun.btc.model.Arbitrage;
import com.ergun.btc.model.Currency;
import com.ergun.btc.model.Price;
import com.ergun.btc.service.TrackerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.tomcat.util.bcel.Const;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

@Service
public class TrackerServiceImpl implements TrackerService {

    @Autowired
    private ArbitrageDao arbitrageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerServiceImpl.class);
    private static final NumberFormat TRY_FORMATTER = NumberFormat.getInstance();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Scheduled(fixedRate = 300000)
    public void scheduledRuns() throws IOException, ParseException {
        this.runTracker();
    }

    @Override
    public Arbitrage runTracker() throws IOException, ParseException {

        Price bitstampValue = getBitstampValue();
        Price btcturkValue = getBtcturkValue();
        Double tryUsd = getTryUsdValue();

        LOGGER.info("Bitstamp -> {}, Btcturk -> {}, USD/TRY -> {}", bitstampValue, btcturkValue, tryUsd);

        Arbitrage arbitrage = new Arbitrage(bitstampValue,btcturkValue,tryUsd);
        this.calculateSyntheticFields(arbitrage);

        LOGGER.info("Arbitrage -> {}", arbitrage);
        arbitrageDao.save(arbitrage);

        return arbitrage;
    }

    private Price getBitstampValue() throws IOException, ParseException {
        String json = Jsoup.connect(Constants.BITSTAMP_URL).ignoreContentType(true).execute().body();
        ObjectNode nodes = MAPPER.readValue(json, ObjectNode.class);
        String lastPrice = nodes.get("last").asText();

        return new Price(Double.parseDouble(lastPrice), Currency.USD);
    }

    private Price getBtcturkValue() throws IOException, ParseException {
        Document doc = Jsoup.connect(Constants.BTCTURK_URL).get();
        Element priceElement = doc.getElementById("askPrice");

        Number amount = TRY_FORMATTER.parse(priceElement.text());
        return new Price(amount.doubleValue(), Currency.TRY);
    }

    private Double getTryUsdValue() throws IOException, ParseException {
        String json = Jsoup.connect(Constants.DOVIZ_URL).ignoreContentType(true).execute().body();
        ObjectNode nodes = MAPPER.readValue(json, ObjectNode.class);
        String usdSellPrice = nodes.get("selling").asText();

        Number amount = TRY_FORMATTER.parse(usdSellPrice);
        return amount.doubleValue();
    }

    private void calculateSyntheticFields(Arbitrage arbitrage) {
        arbitrage.setProfit(calculateProfit(arbitrage));
        arbitrage.setNetProfit(calculateNetProfit(arbitrage));
        arbitrage.setRawPercentage(calculateRawPercentage(arbitrage));
    }

    private Double calculateRawPercentage(Arbitrage arbitrage) {
        return arbitrage.getProfit() / arbitrage.getBtcturkPrice().getAmount();
    }

    private Double calculateProfit(Arbitrage arbitrage){
        Double bitstampTryValue = arbitrage.getBitstampPrice().getAmount() * arbitrage.getTryUsd();
        return bitstampTryValue - arbitrage.getBtcturkPrice().getAmount();
    }

    private Double calculateNetProfit(Arbitrage arbitrage){
        Double val = arbitrage.getBitstampPrice().getAmount() * (1 - Constants.BITSTAMP_BTC_USD_FEE_PERC);
        val *= (1 - Constants.BITSTAMP_DEPOSIT_FEE_PERC);
        val *= arbitrage.getTryUsd();
        val *= (1 - Constants.BTCTURK_TAKER_FEE_PERC);
        val -= Constants.YKB_SWIFT_COST.getAmount();

        return val - arbitrage.getBtcturkPrice().getAmount();
    }


}
