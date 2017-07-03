package com.ergun.btc.service;


import java.io.IOException;
import java.text.ParseException;

public interface TrackerService {
    void runTracker() throws IOException, ParseException;
}
