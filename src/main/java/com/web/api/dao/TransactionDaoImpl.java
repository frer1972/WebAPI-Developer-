package com.web.api.dao;

import java.time.LocalDate;
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
        transactions.put(1, new Transaction(1,1,LocalDate.of(2022, 1, 3),30,0));
        transactions.put(2, new Transaction(2,1,LocalDate.of(2022, 1, 9),110,0));
        transactions.put(3, new Transaction(3,1,LocalDate.of(2022, 1, 5),150,0));
        transactions.put(4, new Transaction(4,2,LocalDate.of(2022, 1, 2),200,0));
        transactions.put(5, new Transaction(5,3,LocalDate.of(2022, 1, 12),350,0));
        transactions.put(6, new Transaction(6,4,LocalDate.of(2022, 3, 13),45,0));
        transactions.put(7, new Transaction(7,5,LocalDate.of(2022, 1, 14),132,0));
        transactions.put(8, new Transaction(8,3,LocalDate.of(2022, 2, 20),237,0));
        transactions.put(9, new Transaction(9,2,LocalDate.of(2022, 1, 17),99,0));
        transactions.put(10, new Transaction(10,4,LocalDate.of(2022, 3, 3),101,0));
        transactions.put(11, new Transaction(11,5,LocalDate.of(2022, 2, 3),120,0));
        transactions.put(12, new Transaction(12,3,LocalDate.of(2022, 3, 18),185,0));
        transactions.put(13, new Transaction(13,2,LocalDate.of(2022, 1, 30),322,0));
        transactions.put(14, new Transaction(14,5,LocalDate.of(2022, 3, 30),115,0));
        transactions.put(15, new Transaction(15,6,LocalDate.of(2022, 1, 19),1016,0));
        transactions.put(16, new Transaction(16,7,LocalDate.of(2022, 1, 24),1049,0));
        transactions.put(17, new Transaction(17,1,LocalDate.of(2022, 2, 7),982,0));
        transactions.put(18, new Transaction(18,6,LocalDate.of(2022, 2, 27),1097,0));
        transactions.put(19, new Transaction(19,2,LocalDate.of(2022, 2, 27),234,0));
        transactions.put(20, new Transaction(20,7,LocalDate.of(2022, 2, 11),63,0));
        transactions.put(21, new Transaction(21,8,LocalDate.of(2022, 1, 21),74,0));
        transactions.put(22, new Transaction(22,2,LocalDate.of(2022, 3, 11),50,0));
        transactions.put(23, new Transaction(23,8,LocalDate.of(2022, 2, 15),51,0));
        transactions.put(24, new Transaction(24,8,LocalDate.of(2022, 3, 3),93,0));
        transactions.put(25, new Transaction(25,9,LocalDate.of(2022, 1, 28),100,0));
        transactions.put(26, new Transaction(26,3,LocalDate.of(2022, 3, 23),127,0));
        transactions.put(27, new Transaction(27,1,LocalDate.of(2022, 1, 19),218,0));
        transactions.put(28, new Transaction(28,9,LocalDate.of(2022, 2, 26),467,0));
        transactions.put(29, new Transaction(29,9,LocalDate.of(2022, 4, 29),15,0));
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
