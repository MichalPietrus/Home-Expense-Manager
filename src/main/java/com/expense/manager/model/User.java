package com.expense.manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null && transaction.getAmount() != null && transaction.getCategory() != null) {
            transactions.add(transaction);
            transaction.setUser(this);
        }
    }

    public void addCategory(Category category) {
        if (category != null) {
            categories.add(category);
            category.setUser(this);
        }
    }

    public void addBasicIncomeAndOutcomeList() {
        categories = new ArrayList<>();
        categories.addAll(Arrays.asList(new Category("Food", "outcome"),
                new Category("Rent", "outcome"),
                new Category("Credit", "outcome"),
                new Category("Bills", "outcome"),
                new Category("Health", "outcome"),
                new Category("Hygiene", "outcome"),
                new Category("Chemicals", "outcome"),
                new Category("Clothes", "outcome"),
                new Category("Relaxation", "outcome"),
                new Category("Transport", "outcome"),
                new Category("Others", "outcome"),
                new Category("Salary", "income")));
        categories.forEach(category -> category.setUser(this));
    }

}
