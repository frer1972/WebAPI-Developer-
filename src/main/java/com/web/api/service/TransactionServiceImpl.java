package com.web.api.service;

import java.util.List;
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
        Transaction transaction = transactionDao.getOne(id);
        transaction.setPoint(getPoints(transaction.getSale()));
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transaction = transactionDao.getAll();
        transaction.stream().forEach(p -> p.setPoint(getPoints(p.getSale())));
        return transaction.stream().collect(Collectors.toList());
    }

    @Override
    public long getPoints(double value) {
        long points = 0;
        long diff = (long) (value - ConstantUtil.HUNDRED);
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

}
