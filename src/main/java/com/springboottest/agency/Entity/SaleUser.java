package com.springboottest.agency.Entity;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table(name = "sale")
public class SaleUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SalesId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductUser product;


    // ✅ Default constructor (VERY IMPORTANT)
    public SaleUser() {
    }

   public SaleUser(int quantity, LocalDate date, double sellingPrice, ProductUser product) {
    this.quantity = quantity;
    this.date = date;
    this.sellingPrice = sellingPrice;
    this.product = product;
}
}
