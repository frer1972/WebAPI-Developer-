package com.web.api.core;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    
    private int id;
    private int customerId;
    private LocalDate date;
    private double sale;
    private long point;

}
