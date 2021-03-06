package com.web.api.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.api.core.Transaction;
import com.web.api.dto.TransactionMonth;
import com.web.api.service.TransactionService;

@RestController
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;    
   
    @GetMapping("/")
    public List<Transaction> getAllCustomerPointsByTransaction(){
        return transactionService.getAllPointsByCustomersTransactions();
    }
    
    @GetMapping("/month")
    public List<TransactionMonth> getAllCustomersPointsByMonths(){
        return transactionService.getAllPointsByCustomersMonths();
    }

}
