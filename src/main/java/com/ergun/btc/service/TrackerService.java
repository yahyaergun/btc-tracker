package com.ergun.btc.service;


import com.ergun.btc.model.Arbitrage;

import java.io.IOException;
import java.text.ParseException;

public interface TrackerService {
    Arbitrage runTracker() throws IOException, ParseException;
}
