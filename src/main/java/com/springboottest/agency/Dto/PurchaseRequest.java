package com.springboottest.agency.Dto;

import lombok.Data;

@Data
public class PurchaseRequest {
  private Long productId;
    private int quantity;
    private String vendor;
}
