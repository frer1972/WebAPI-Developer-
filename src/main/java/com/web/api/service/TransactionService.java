package com.web.api.service;

import java.util.List;

import com.web.api.core.Transaction;

public interface TransactionService {

    Transaction getTransaction(int id);
    List<Transaction> getAllTransactions();
    long getPoints(double value);
}
