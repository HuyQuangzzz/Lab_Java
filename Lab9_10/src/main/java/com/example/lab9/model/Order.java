package com.example.lab9.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @Column(name="order_number")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long orderNumber;

    @Min(value=0)
    @NotNull
    @Column(name="total_selling_price")
    private Double totalSellingPrice;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    private boolean active;

    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<Product> products;
}
