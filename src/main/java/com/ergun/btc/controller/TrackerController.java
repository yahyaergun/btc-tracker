package com.ergun.btc.controller;

import com.ergun.btc.service.TrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class TrackerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerController.class);

    @Autowired
    private TrackerService trackerService;

    @RequestMapping("/")
    public void home(){
        try {
            trackerService.runTracker();
        } catch (ParseException | IOException e) {
            LOGGER.error("Error on tracker service, details: {}",e);
            throw new RuntimeException(e);
        }
    }

}
