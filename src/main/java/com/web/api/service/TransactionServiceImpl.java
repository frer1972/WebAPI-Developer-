package com.web.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.core.Transaction;
import com.web.api.core.TransactionMonth;
import com.web.api.dao.TransactionDao;
import com.web.api.util.constant.ConstantUtil;

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
        transaction = transaction.stream().sorted(Comparator.comparing(Transaction::getCustomerId).thenComparing(Transaction::getDate)).collect(Collectors.toList());
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

    @Override
    public List<TransactionMonth> getPointsMonths() {
        List<Transaction> transactions = transactionDao.getAll();
        List<TransactionMonth> transactionMonths = new ArrayList<>();
        transactions.sort(Comparator.comparing(Transaction::getCustomerId).thenComparing(Transaction::getDate));
        transactions.stream().forEach(p -> p.setPoint(getPoints(p.getSale())));

        Map<Integer, List<Transaction>> transactionsMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        transactionsMap.forEach((customerId, transactionsList) -> getCustomerTransaction(customerId,transactionsList,transactionMonths));

        return transactionMonths;
    }
    
    private void getCustomerTransaction(Integer customerId, List<Transaction> transacction, List<TransactionMonth> transactionMonths){        
        long points = 0;        
        Optional<LocalDate> maxDate = transacction.stream().max(Comparator.comparing(Transaction::getDate)).map(Transaction::getDate);       
        LocalDate minDate = maxDate.get().minusMonths(3);
        for(Transaction transaction : transacction) {
            LocalDate date = transaction.getDate();
            if ((maxDate.get().isAfter(date) || maxDate.get().equals(date)) && (minDate.isBefore(date) || minDate.equals(date))) {
              points += transaction.getPoint();
          }
        }        
        transactionMonths.add(new TransactionMonth(customerId, points));        
    }

}
