package com.expense.manager.Pojo;

import lombok.Data;

@Data
public class TransactionsFilter {

    private String fromDate;
    private String toDate;
    private String filter;

}
