package com.expense.manager.Pojo;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class CategoryRanking {

    private String name;

    private Long amount;

    private double percentageContribution;

    public String printPercentageContribution() {
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        return decimalFormat.format(this.percentageContribution);
    }

}
