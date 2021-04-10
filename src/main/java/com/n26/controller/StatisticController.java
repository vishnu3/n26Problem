package com.n26.controller;

import com.n26.models.Statistics;
import com.n26.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {
    @Autowired
    private StatisticService statisticService;


    @GetMapping("/statistics")
    public ResponseEntity<Statistics> analytics(){
        return null;
    }
}
