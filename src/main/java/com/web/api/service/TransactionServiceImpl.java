package com.web.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.api.core.Transaction;
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
        transaction.stream().forEach(p -> p.setPoint(getPoints(p.getSale())));
        return transaction.stream().collect(Collectors.toList());
    }

    private long getPoints(double value) {
        long points = 0;
        long balance = 0;
        balance = (long) (value - ConstantUtil.FIFTY);
        if (balance > ConstantUtil.FIFTY) {
            long diff = balance - ConstantUtil.FIFTY;
            points += (ConstantUtil.TWO_POINT * diff) + ConstantUtil.FIFTY;
        } else if(balance > 0) {
            points += balance;
        }
        return points;
    }

}
