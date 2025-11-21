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

   // 👉 Fix null stock
   if (productUser.getProductStock() == null) {
       productUser.setProductStock(0);
    }

    //👉 Fix null price
    if (productUser.getProductPrice() == null) {
       productUser.setProductPrice(0.0);
   }

    // 🔥 Calculate total price
    double total = productUser.getProductPrice() * productUser.getProductStock();
    productUser.setTotalPrice(total);

    return productRepo.save(productUser);
}


    // ✅ Update a product-Price
    public void updateProductPrice(Long id, double newPrice) {
    ProductUser product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    //UPDATE PRICE
    product.setProductPrice(newPrice);

    // 🔥 Recalculate total price
        double total = product.getProductPrice() * product.getProductStock();
        product.setTotalPrice(total);

    //SAVE UPDATED PRODUCT
    productRepo.save(product);
}


    
    // ✅ Purchase product (update stock + total price)
    public void purchaseProduct(Long productId, int quantity, String vendor) {

        ProductUser product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // update stock
        int updatedStock = product.getProductStock() + quantity;
        product.setProductStock(updatedStock);

        // 🔥 Recalculate total price
        double total = product.getProductPrice() * updatedStock;
        product.setTotalPrice(total);

        productRepo.save(product);

        // create purchase entry
        PurchaseUser purchaseUser = new PurchaseUser();
        purchaseUser.setVendor(vendor);
        purchaseUser.setQuantity(quantity);
        purchaseUser.setDate(LocalDate.now());
        purchaseUser.setProduct(product);

        purchaseRepo.save(purchaseUser);
    }



    // ✅ Sell product (update stock + total price)
    public void saleProduct(Long productId, int quantity, double sellingPrice) {

        ProductUser product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getProductStock() >= quantity) {

            int updatedStock = product.getProductStock() - quantity;
            product.setProductStock(updatedStock);

            // 🔥 Recalculate total price
            double total = product.getProductPrice() * updatedStock;
            product.setTotalPrice(total);

            productRepo.save(product);

            SaleUser sale = new SaleUser(quantity, LocalDate.now(), sellingPrice, product);
            saleRepo.save(sale);
        } else {
            throw new RuntimeException("Not enough stock to sell!");
        }
    }



}
