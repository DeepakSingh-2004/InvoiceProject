package com.springboottest.agency.Entity;

import java.time.LocalDate;

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
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Data
@Table(name = "purchase")
public class PurchaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PurchaseId;

    @Column(nullable = false)
      private String vendor;

    @Column(nullable = false)
     private int quantity;

    @Column(nullable = false)
     private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
     private ProductUser product;

     // ✅ Default constructor (VERY IMPORTANT)
    public PurchaseUser() {
    }
    
    
    // PurchaseUser purchaseUser = new PurchaseUser(vendor, quantity, LocalDate.now(), product);
   public PurchaseUser(String vendor, int quantity, LocalDate date, ProductUser product) {
    this.vendor = vendor;
    this.quantity = quantity;
    this.date = date;
    this.product = product;
}

}

    

