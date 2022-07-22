package com.web.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.core.Transaction;
import com.web.api.dao.TransactionDao;
import com.web.api.dto.TransactionMonth;
import com.web.api.util.constant.ConstantUtil;

/**
 * 
 * @author fespinoza Service to generate the points per month.
 *
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

   
    @Override
    public List<Transaction> getAllPointsByCustomersTransactions() {
        List<Transaction> transaction = transactionDao.getAllTransactions();
        
        /*
         * Ordering for customerid and date to present the data.
         */
        transaction = transaction.stream()
                .sorted(Comparator.comparing(Transaction::getCustomerId).thenComparing(Transaction::getDate))
                .collect(Collectors.toUnmodifiableList());
        
        /*
         * In this line of code we have to iterate the list to get the points for each customer transaction.
        */
        transaction.stream().forEach(p -> p.setPoint(getPointPerTransaction(p.getSale())));

        return transaction;
    }
    
    /**
     * This method calculates the point for transaction. Method only used in this service.
     * @param value
     * @return points
     */
    private long getPointPerTransaction(double value) {
        long points = 0;
        long diff = 0;
        diff = (long) (value - ConstantUtil.HUNDRED);
        if (diff > 0) {
            points += ConstantUtil.TWO_POINT * diff;
        }
        diff = (long) (value - ConstantUtil.FIFTY);
        if (diff > 0) {
            long numbers = diff / ConstantUtil.FIFTY;
            points += ConstantUtil.ONE_POINT * numbers * ConstantUtil.FIFTY;
        }         
        return points;
    }

    
    @Override
    public List<TransactionMonth> getAllPointsByCustomersMonths() {
        List<Transaction> transactions = transactionDao.getAllTransactions();
        List<TransactionMonth> transactionMonths = new ArrayList<>();
        transactions.stream().forEach(p -> p.setPoint(getPointPerTransaction(p.getSale())));
        
        /*
         * The logic is to group by customer. we can obtain every transaction per month of each customer. 
         */
        Map<Integer, List<Transaction>> transactionsMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));
        /*
         * Calculating transactions per month for each customer.
         */
        transactionsMap.forEach((customerId, transactionsList) -> getCustomerTransaction(customerId, transactionsList,
                transactionMonths));
        /*
         * Sending report list sorted by customerid and month.
         */
        return transactionMonths.stream()
                .sorted(Comparator.comparing(TransactionMonth::getCustomerId).thenComparing(TransactionMonth::getMonth))
                .collect(Collectors.toList());
    }
    
    
    /**
     * This method calculates the point for each customer month. Method only used in this service.
     * @param customerId
     * @param transacction
     * @param transactionMonths
     */
    private void getCustomerTransaction(Integer customerId, List<Transaction> transacction,
            List<TransactionMonth> transactionMonths) {
        
        /*
         * Getting transactions per customer
         */
        List<TransactionMonth> transactionMonth = transacction.stream()
                .map(p -> new TransactionMonth(p.getCustomerId(), p.getDate().getMonthValue(), p.getPoint()))
                .collect(Collectors.toList());
        /*
         * Obtaining the max month of the customer transaction.
         */
        Optional<Integer> maxMonth = transactionMonth.stream().map(TransactionMonth::getMonth).reduce(Integer::max); 
        /*
         * Grouping the transactions of each month to total them.
         */
        Map<Integer, List<TransactionMonth>> mapTransactionMonth = transactionMonth.stream()
                .collect(Collectors.groupingBy(TransactionMonth::getMonth));
        /*
         * Getting the total per month.  
         */
        mapTransactionMonth.forEach((month, transactionMonthList) -> {
            long points = transactionMonthList.stream().map(TransactionMonth::getPoint).reduce(0l, Long::sum);
            transactionMonths.add(new TransactionMonth(customerId, month, points));
        });
        
        /*
         * Collecting all months found. 
         */
        Set<Integer> months = transactionMonth.stream().map(TransactionMonth::getMonth).collect(Collectors.toSet());
        
        /*
         * Adding the missing months in the report list.
         */
        for (int i = 1; i <= maxMonth.get(); i++) {
            if (!months.contains(i)) {
                transactionMonths.add(new TransactionMonth(customerId, i, 0));
            }
        }
    }

}
