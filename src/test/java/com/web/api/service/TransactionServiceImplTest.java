package com.web.api.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.web.api.core.Transaction;
import com.web.api.dao.TransactionDaoImpl;
import com.web.api.dto.TransactionMonth;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    
    @Mock
    private TransactionDaoImpl transactionDao;
    
    @InjectMocks
    private TransactionServiceImpl transactionService;
    
    private static final int MONTH_FIRST_TEST = 656;
    private static final int MONTH_SECOND_TEST = 2664;
    private static final int TEST1 = 0;    
    private static final int TEST2 = 200;    
    private static final int TEST3 = 70;    
    private static final int TEST4 = 386;    
    private static final int TEST5 = 2664;    
    private List<Transaction> transactions;
    
    @BeforeEach
    public void setup() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(1,1,LocalDate.of(2022, 1, 3),30,0));
        transactions.add(new Transaction(2,1,LocalDate.of(2022, 1, 9),110,0));
        transactions.add(new Transaction(3,1,LocalDate.of(2022, 1, 5),150,0));
        transactions.add(new Transaction(17,1,LocalDate.of(2022, 2, 7),982,0));
        transactions.add(new Transaction(27,1,LocalDate.of(2022, 1, 19),218,0));
    }
    
    @Test
    public void getPointsMonthsTest() {  
        when(transactionDao.getAll()).thenReturn(transactions);
        List<TransactionMonth> transactionAll = transactionService.getPointsMonths();
        assertEquals(MONTH_FIRST_TEST, transactionAll.get(0).getPoint());
        assertEquals(MONTH_SECOND_TEST,transactionAll.get(1).getPoint());
        assertNotEquals(MONTH_SECOND_TEST, transactionAll.get(0).getPoint());
    }
    
    @Test
    public void getPointPerTransaction() {
        when(transactionDao.getAll()).thenReturn(transactions);
        List<Transaction> transactionPerMonth = transactionService.getAllTransactions();
        assertEquals(TEST1,transactionPerMonth.get(0).getPoint());
        assertEquals(TEST2,transactionPerMonth.get(1).getPoint());
        assertEquals(TEST3,transactionPerMonth.get(2).getPoint());
        assertEquals(TEST4,transactionPerMonth.get(3).getPoint());
        assertEquals(TEST5,transactionPerMonth.get(4).getPoint());
    }
}
