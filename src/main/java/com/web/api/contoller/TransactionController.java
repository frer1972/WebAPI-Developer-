package com.web.api.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Transaction> getOneTransaction(@PathVariable("id") int id) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionService.getTransaction(id));
        if(transaction.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(transaction.get());
    }
    
    @GetMapping("/")
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

}
