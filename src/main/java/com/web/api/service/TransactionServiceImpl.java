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
    public Transaction getTransaction(int id) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionDao.getOne(id));
        if (transaction.isPresent()) {
            transaction.get().setPoint(getPoints(transaction.get().getSale()));
            return transaction.get();
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transaction = transactionDao.getAll();
        // This return the Collection sorted by customeid and date, this has
        // good performance since we use the resource of stream API. Reducing
        // line of code.
        transaction = transaction.stream()
                .sorted(Comparator.comparing(Transaction::getCustomerId).thenComparing(Transaction::getDate))
                .collect(Collectors.toList());
        transaction.stream().forEach(p -> p.setPoint(getPoints(p.getSale())));
        return transaction;
    }

    private long getPoints(double value) {
        long points = 0;
        long balance = 0;
        balance = (long) (value - ConstantUtil.FIFTY);
        if (balance > ConstantUtil.FIFTY) {
            long diff = balance - ConstantUtil.FIFTY;
            points += (ConstantUtil.TWO_POINT * diff) + ConstantUtil.FIFTY;
        } else if (balance > 0) {
            points += balance;
        }
        return points;
    }

    /**
     * The method is using java stream API giving a good performing to process
     * the collections of objects
     */
    @Override
    public List<TransactionMonth> getPointsMonths() {
        List<Transaction> transactions = transactionDao.getAll();
        List<TransactionMonth> transactionMonths = new ArrayList<>();
        transactions.stream().forEach(p -> p.setPoint(getPoints(p.getSale())));
        // This line is performing because, We can group the data in only one
        // line and also the degree of complexity is being reduced.
        Map<Integer, List<Transaction>> transactionsMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));
        // It is performing forEach to interact in the collection API and
        // generate the new values.
        transactionsMap.forEach((customerId, transactionsList) -> getCustomerTransaction(customerId, transactionsList,
                transactionMonths));
        // This return the Collection sorted by customeid and month, this has
        // good performance since we use the resource of stream API. Reducing
        // line of code.
        return transactionMonths.stream()
                .sorted(Comparator.comparing(TransactionMonth::getCustomerId).thenComparing(TransactionMonth::getMonth))
                .collect(Collectors.toList());
    }

    private void getCustomerTransaction(Integer customerId, List<Transaction> transacction,
            List<TransactionMonth> transactionMonths) {
        // Assigning the data a other List, the assignment is quick and easy.
        List<TransactionMonth> transactionMonth = transacction.stream()
                .map(p -> new TransactionMonth(p.getCustomerId(), p.getDate().getMonthValue(), p.getPoint()))
                .collect(Collectors.toList());
        // We don't have to worry for null pointer.
        Optional<Integer> maxMonth = transactionMonth.stream().map(TransactionMonth::getMonth).reduce(Integer::max);
        // This line is performing because, We can group the data in only one
        // line and also the degree of complexity is being reduced.
        Map<Integer, List<TransactionMonth>> mapTransactionMonth = transactionMonth.stream()
                .collect(Collectors.groupingBy(TransactionMonth::getMonth));
        mapTransactionMonth.forEach((month, transactionMonthList) -> {
            long points = transactionMonthList.stream().map(TransactionMonth::getPoint).reduce(0l, Long::sum);
            transactionMonths.add(new TransactionMonth(customerId, month, points));
        });
        // Stream API let us to collect in different kind of collection.
        Set<Integer> months = transactionMonth.stream().map(TransactionMonth::getMonth).collect(Collectors.toSet());
        for (int i = 1; i <= maxMonth.get(); i++) {
            if (!months.contains(i)) {
                transactionMonths.add(new TransactionMonth(customerId, i, 0));
            }
        }
    }

}
