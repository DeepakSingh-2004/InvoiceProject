package com.springboottest.agency.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "product_stock")
    private Integer productStock;

    private Integer quantity;

    private String unit;

    @Column(nullable = false)
    private Double productPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    // 👉 FIXED getTotalPrice()
    public Double calculateTotalPrice() {
        if (productPrice == null || productStock == null) {
            return 0.0;
        }
        return productPrice * productStock;
    }
}
