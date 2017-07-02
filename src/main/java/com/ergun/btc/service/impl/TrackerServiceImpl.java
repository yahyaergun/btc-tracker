package com.ergun.btc.service.impl;

import com.ergun.btc.Constants;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

@Service
public class TrackerServiceImpl implements TrackerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerServiceImpl.class);
    private static final NumberFormat TRY_FORMATTER = NumberFormat.getInstance();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void runTracker() {
        try {
            Price bitstampValue = getBitstampValue();
            Price btcturkValue = getBtcturkValue();
            LOGGER.info("Bitstamp -> {}, Btcturk -> {}", bitstampValue, btcturkValue);
        } catch (Exception e){
            LOGGER.error("Could not get bitcoin prices, reason: {}", e.getLocalizedMessage());
        }
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


}
