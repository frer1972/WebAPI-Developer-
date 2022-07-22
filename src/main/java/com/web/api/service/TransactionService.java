package com.web.api.service;

import java.util.List;

import com.web.api.core.Transaction;
import com.web.api.dto.TransactionMonth;

public interface TransactionService {
    
    List<Transaction> getAllPointsByCustomersTransactions();
    List<TransactionMonth> getAllPointsByCustomersMonths();
    
}
