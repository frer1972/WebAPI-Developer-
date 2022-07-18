package com.web.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.web.api.core.Transaction;
import com.web.api.dao.TransactionDaoImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    
    @Mock
    private TransactionDaoImpl transactionDao;
    
    @InjectMocks
    private TransactionServiceImpl transactionService;
    
    @Test 
    public void getAllTest() {
        Transaction t1 = new Transaction(1,1,LocalDate.of(2022, 1, 3),30,0);
        Transaction t2 = new Transaction(11,1,LocalDate.of(2022, 2, 3),120,0);
        
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        
        when(transactionDao.getAll()).thenReturn(transactions);
        
        List<Transaction> transactionAll = transactionService.getAllTransactions();
        
        assertEquals(120.0, transactionAll.get(1).getSale());
    }
    
    @Test 
    public void getSizeTest() {
        Transaction t1 = new Transaction(1,1,LocalDate.of(2022, 1, 3),30,0);
        Transaction t2 = new Transaction(11,1,LocalDate.of(2022, 2, 3),120,0);
        
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        
        when(transactionDao.getAll()).thenReturn(transactions);
        
        List<Transaction> transactionAll = transactionService.getAllTransactions();
        
        assertEquals(2, transactionAll.size());
    }
    
    @Test
    public void getTransactionTest() {
        Transaction transaction = new Transaction(11,1,LocalDate.of(2022, 1, 3),120,90);
        
        when(transactionDao.getOne(11)).thenReturn(transaction);
        
        Transaction transactionTest = transactionService.getTransaction(11);
        assertEquals(90, transactionTest.getPoint());
    }
    
    

}
