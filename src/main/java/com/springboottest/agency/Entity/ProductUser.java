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
     private Integer productStock;

     @Column(nullable = false)
     private Double productPrice;

    
     @Column(name = "TotalPrice")
     private Double totalPrice;

    public int getTotalPrice() {

        int totalPrice1 = 0;
    if (productPrice != null && productStock != null) {
        totalPrice1 = (int) (productPrice * productStock);
    }
    return totalPrice1;
        }


     // ✅ Default constructor (VERY IMPORTANT)
    public ProductUser() {
    }

    
}