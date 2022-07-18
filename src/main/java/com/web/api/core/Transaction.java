package com.web.api.core;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    
    private int id;
    private int customerId;
    private LocalDate date;
    private double sale;
    private long point;

}
