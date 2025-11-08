package com.springboottest.agency.Service;

import java.time.LocalDate;
import java.util.List;
import com.springboottest.agency.Repository.PurchaseRepo;
import com.springboottest.agency.Repository.SaleRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboottest.agency.Entity.ProductUser;
import com.springboottest.agency.Entity.PurchaseUser;
import com.springboottest.agency.Entity.SaleUser;
import com.springboottest.agency.Repository.ProductRepo;

@Service
public class InventoryService {


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private SaleRepo saleRepo;

    
    public List<ProductUser> getAll() {
    return productRepo.findAll();
}


    // ✅ Get all products
    public ResponseEntity<List<ProductUser>> getAllProducts() {
        List<ProductUser> products = productRepo.findAll();
        return ResponseEntity.ok(products);
    }

      // ✅ Add a product
    public ProductUser addProduct(ProductUser productUser) {
        return productRepo.save(productUser);
    }

    // ✅ Purchase product
    public void purchaseProduct(Long productId, int quantity, String vendor){
        ProductUser product = productRepo.findById(productId).orElseThrow();

         // update stock
        product.setProductStock(product.getProductStock() + quantity);
        productRepo.save(product);      

       // create purchase entry 
    PurchaseUser purchaseUser = new PurchaseUser();
    purchaseUser.setVendor(vendor);
    purchaseUser.setQuantity(quantity);
    purchaseUser.setDate(LocalDate.now());
    purchaseUser.setProduct(product);


    purchaseRepo.save(purchaseUser);
    }

    // ✅ Sell Product
    public void saleProduct(Long productId, int quantity, double sellingPrice){
        ProductUser product = productRepo.findById(productId).orElseThrow();
        if (product.getProductStock() >= quantity) {
            product.setProductStock(product.getProductStock() - quantity);
            productRepo.save(product);

            SaleUser sale = new SaleUser (quantity, LocalDate.now(), sellingPrice, product);
            saleRepo.save(sale);
        }
    }


}
