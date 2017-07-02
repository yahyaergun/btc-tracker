package com.ergun.btc.controller;

import com.ergun.btc.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @RequestMapping("/")
    public void home(){
        trackerService.runTracker();
    }

}
