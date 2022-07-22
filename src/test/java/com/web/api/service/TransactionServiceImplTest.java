package com.web.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    // Data test for customer one
    private static final int CUSTOMERONE_MONTH_FIRST_TEST = 656;
    private static final int CUSTOMERONE_MONTH_SECOND_TEST = 2664;
    private static final int CUSTOMERONE_TEST1 = 0;
    private static final int CUSTOMERONE_TEST2 = 200;
    private static final int CUSTOMERONE_TEST3 = 70;
    private static final int CUSTOMERONE_TEST4 = 386;
    private static final int CUSTOMERONE_TEST5 = 2664;

    // Data test for customer two
    private static final int CUSTOMERTWO_MONTH_FIRST_TEST = 1044;
    private static final int CUSTOMERTWO_MONTH_SECOND_TEST = 418;
    private static final int CUSTOMERTWO_MONTH_THIRD_TEST = 0;
    private static final int CUSTOMERTWO_TEST1 = 350;
    private static final int CUSTOMERTWO_TEST2 = 0;
    private static final int CUSTOMERTWO_TEST3 = 694;
    private static final int CUSTOMERTWO_TEST4 = 418;
    private static final int CUSTOMERTWO_TEST5 = 0;
    private List<Transaction> transactions;

    @BeforeEach
    public void setup() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 1, LocalDate.of(2022, 1, 3), 30, 0));
        transactions.add(new Transaction(2, 1, LocalDate.of(2022, 1, 9), 110, 0));
        transactions.add(new Transaction(3, 1, LocalDate.of(2022, 1, 5), 150, 0));
        transactions.add(new Transaction(17, 1, LocalDate.of(2022, 2, 7), 982, 0));
        transactions.add(new Transaction(27, 1, LocalDate.of(2022, 1, 19), 218, 0));
        transactions.add(new Transaction(4, 2, LocalDate.of(2022, 1, 2), 200, 0));
        transactions.add(new Transaction(9, 2, LocalDate.of(2022, 1, 17), 99, 0));
        transactions.add(new Transaction(13, 2, LocalDate.of(2022, 1, 30), 322, 0));
        transactions.add(new Transaction(19, 2, LocalDate.of(2022, 2, 27), 234, 0));
        transactions.add(new Transaction(22, 2, LocalDate.of(2022, 3, 11), 50, 0));
    }

    @Test
    public void getPointsMonthsTest() {
        when(transactionDao.getAllTransactions()).thenReturn(transactions);
        List<TransactionMonth> transactionAll = transactionService.getAllPointsByCustomersMonths();

        // Test for the first customer
        assertEquals(CUSTOMERONE_MONTH_FIRST_TEST, transactionAll.get(0).getPoint());
        assertEquals(CUSTOMERONE_MONTH_SECOND_TEST, transactionAll.get(1).getPoint());
        assertNotEquals(CUSTOMERONE_MONTH_SECOND_TEST, transactionAll.get(0).getPoint());

        // Test for the second customer
        assertEquals(CUSTOMERTWO_MONTH_FIRST_TEST, transactionAll.get(2).getPoint());
        assertEquals(CUSTOMERTWO_MONTH_SECOND_TEST, transactionAll.get(3).getPoint());
        assertEquals(CUSTOMERTWO_MONTH_THIRD_TEST, transactionAll.get(4).getPoint());

    }

    @Test
    public void getPointPerTransaction() {
        when(transactionDao.getAllTransactions()).thenReturn(transactions);
        List<Transaction> transactionPerMonth = transactionService.getAllPointsByCustomersTransactions();

        // Test for the first customer
        assertEquals(CUSTOMERONE_TEST1, transactionPerMonth.get(0).getPoint());
        assertEquals(CUSTOMERONE_TEST2, transactionPerMonth.get(1).getPoint());
        assertEquals(CUSTOMERONE_TEST3, transactionPerMonth.get(2).getPoint());
        assertEquals(CUSTOMERONE_TEST4, transactionPerMonth.get(3).getPoint());
        assertEquals(CUSTOMERONE_TEST5, transactionPerMonth.get(4).getPoint());

        // Test for the second customer
        assertEquals(CUSTOMERTWO_TEST1, transactionPerMonth.get(5).getPoint());
        assertEquals(CUSTOMERTWO_TEST2, transactionPerMonth.get(6).getPoint());
        assertEquals(CUSTOMERTWO_TEST3, transactionPerMonth.get(7).getPoint());
        assertEquals(CUSTOMERTWO_TEST4, transactionPerMonth.get(8).getPoint());
        assertEquals(CUSTOMERTWO_TEST5, transactionPerMonth.get(9).getPoint());
    }
}
