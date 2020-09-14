package com.expense.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 50)
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
