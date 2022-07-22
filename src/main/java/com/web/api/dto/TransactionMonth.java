package com.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionMonth {
    
    private int customerId;
    private int month;
    private long point;

}
