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
        transactions.put(12, new Transaction(12,185,0));
        transactions.put(13, new Transaction(13,322,0));
        transactions.put(14, new Transaction(14,115,0));
        transactions.put(15, new Transaction(15,1016,0));
        transactions.put(16, new Transaction(16,1049,0));
        transactions.put(17, new Transaction(17,982,0));
        transactions.put(18, new Transaction(18,1097,0));
        transactions.put(19, new Transaction(19,234,0));
        transactions.put(20, new Transaction(20,63,0));
        transactions.put(21, new Transaction(21,74,0));
        transactions.put(22, new Transaction(22,50,0));
        transactions.put(23, new Transaction(23,51,0));
        transactions.put(24, new Transaction(24,93,0));
        transactions.put(25, new Transaction(25,100,0));
        transactions.put(26, new Transaction(26,127,0));
        transactions.put(27, new Transaction(27,218,0));
        transactions.put(28, new Transaction(28,467,0));
        transactions.put(29, new Transaction(29,15,0));
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
