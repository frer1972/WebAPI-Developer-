package com.web.api.dao;

import java.util.List;

import com.web.api.core.Transaction;

public interface TransactionDao {
    
    Transaction getOne(int id);
    List<Transaction> getAllTransactions();

}
