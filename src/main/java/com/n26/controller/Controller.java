package com.n26.controller;

import com.n26.models.Statistics;
import com.n26.models.Transaction;
import com.n26.services.StatisticService;
import com.n26.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StatisticService statisticService;


    @GetMapping("/statistics")
    public ResponseEntity<Statistics> analytics(){
         return null;
    }

    @PostMapping("/transactions")
    public ResponseEntity<HttpStatus> add(@RequestBody final Transaction transaction) {

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<HttpStatus> delete() {

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
