package com.expense.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    /**
     * income
     * or
     * outcome
     */
    private String type;
    @ManyToOne
    private User user;

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
