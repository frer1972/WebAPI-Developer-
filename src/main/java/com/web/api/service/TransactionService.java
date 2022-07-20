package com.web.api.service;

import java.util.List;

import com.web.api.core.Transaction;
import com.web.api.dto.TransactionMonth;

public interface TransactionService {

    Transaction getTransaction(int id);
    List<Transaction> getAllTransactions();
    List<TransactionMonth> getPointsMonths();
}
