package com.web.api.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.web.api.core.Transaction;

@Repository
public class TransactionDaoImpl implements TransactionDao {
    
    private Map<Integer, Transaction> transactions;
    
    {
        transactions = new HashMap<>();
        transactions.put(1, new Transaction(1,30,0));
        transactions.put(2, new Transaction(2,110,0));
        transactions.put(3, new Transaction(3,150,0));
        transactions.put(4, new Transaction(4,200,0));
        transactions.put(5, new Transaction(5,350,0));
        transactions.put(6, new Transaction(6,45,0));
        transactions.put(7, new Transaction(7,132,0));
        transactions.put(8, new Transaction(8,237,0));
        transactions.put(9, new Transaction(9,99,0));
        transactions.put(10, new Transaction(10,101,0));
        transactions.put(11, new Transaction(11,120,0));
    }

    @Override
    public Transaction getOne(int id) {
        return transactions.get(id);
    }

    
    @Override
    public List<Transaction> getAll() {
        
        Collection<Transaction> collectionT = transactions.values();
        List<Transaction> listTransaction = collectionT.stream().collect(Collectors.toList());
        return listTransaction;
    }

}
