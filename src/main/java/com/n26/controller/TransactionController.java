package com.n26.controller;

import com.n26.models.Transaction;
import com.n26.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<HttpStatus> add(@RequestBody final Transaction transaction) {
        transactionService.add(transaction);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<HttpStatus> delete() {
        transactionService.delete();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
