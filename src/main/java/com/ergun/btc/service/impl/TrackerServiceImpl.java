package com.ergun.btc.service.impl;

import com.ergun.btc.Constants;
import com.ergun.btc.dao.ArbitrageDao;
import com.ergun.btc.model.Arbitrage;
import com.ergun.btc.model.Currency;
import com.ergun.btc.model.Price;
import com.ergun.btc.service.TrackerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        Price koinimValue = getKoinimValue();
        Price paribuValue = getParibuValue();
        Double tryUsd = getTryUsdValue();

        Arbitrage arbitrage = new Arbitrage(bitstampValue,btcturkValue,koinimValue, paribuValue, tryUsd);
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

    private Price getKoinimValue() throws IOException, ParseException {
        String json = Jsoup.connect(Constants.KOINIM_URL).userAgent("Mozilla").ignoreContentType(true).execute().body();
        ObjectNode nodes = MAPPER.readValue(json, ObjectNode.class);
        String lastPrice = nodes.get("sell").asText();

        Number amount = TRY_FORMATTER.parse(lastPrice);
        return new Price(amount.doubleValue(), Currency.TRY);
    }

    private Price getParibuValue() throws IOException, ParseException {
        String json = Jsoup.connect(Constants.PARIBU_URL).userAgent("Mozilla").ignoreContentType(true).execute().body();
        ObjectNode nodes = MAPPER.readValue(json, ObjectNode.class);
        String lastPrice = nodes.get("publicState").get("market_sum").get("day_stats").get("bid").asText();

        Number amount = TRY_FORMATTER.parse(lastPrice);
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
        arbitrage.setBtcturkProfit(calculateBtcturkProfit(arbitrage));
        arbitrage.setKoinimProfit(calculateKoinimProfit(arbitrage));
        arbitrage.setParibuProfit((calculateParibuProfit(arbitrage)));
    }
    private Double calculateBtcturkProfit(Arbitrage arbitrage){
        Double value = arbitrage.getBitstampPrice().getAmount() * arbitrage.getTryUsd();
        return arbitrage.getBtcturkPrice().getAmount() - value;
    }

    private Double calculateKoinimProfit(Arbitrage arbitrage){
        Double value = arbitrage.getBitstampPrice().getAmount() * arbitrage.getTryUsd();
        return arbitrage.getKoinimPrice().getAmount() - value;
    }

    private Double calculateParibuProfit(Arbitrage arbitrage){
        Double value = arbitrage.getBitstampPrice().getAmount() * arbitrage.getTryUsd();
        return arbitrage.getParibuPrice().getAmount() - value;
    }


}
