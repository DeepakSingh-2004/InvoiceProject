package com.springboottest.agency.Entity;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class ProductUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long productId;

     @Column(nullable = false)
     private String productName;
    
     @Column(nullable = false, unique = true)
     private String productCode;

     @Column(nullable = false)
     private int productStock;

     @Column(nullable = false)
     private Double productPrice;

     // ✅ Default constructor (VERY IMPORTANT)
    public ProductUser() {
    }

    
}