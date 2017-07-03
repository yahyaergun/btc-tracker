package com.ergun.btc;

import com.ergun.btc.model.Currency;
import com.ergun.btc.model.Price;

public interface Constants {

    String BITSTAMP_URL = "https://www.bitstamp.net/api/v2/ticker/btcusd/";
    String BTCTURK_URL = "https://www.btcturk.com/";
    String DOVIZ_URL = "http://www.doviz.com/api/v1/currencies/USD/latest";

    Price YKB_SWIFT_COST = new Price(50.00D, Currency.TRY);

    Double BITSTAMP_BTC_USD_FEE_PERC = 0.0025D; // %0.25
    Double BITSTAMP_DEPOSIT_FEE_PERC = 0.0005D; // %0.5
    Double BTCTURK_TAKER_FEE_PERC = 0.004D; // %0.4
    Double BTCTURK_MAKER_FEE_PERC = 0.001D; // %0.1
}
