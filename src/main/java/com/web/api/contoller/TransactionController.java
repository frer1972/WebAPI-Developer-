package com.web.api.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.api.core.Transaction;
import com.web.api.service.TransactionService;

@RestController
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @GetMapping("/{id}")
    public Transaction getOneTransaction(@PathVariable("id") int id) {
        return transactionService.getTransaction(id);
    }
    
    @GetMapping("/")
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

}
