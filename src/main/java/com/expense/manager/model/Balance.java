package com.expense.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startingDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;
    @ManyToOne
    private User user;

}
