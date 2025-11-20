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

        // 🔥 Calculate total price = price * stock
        double total = productUser.getProductPrice() * productUser.getProductStock();
        productUser.setTotalPrice(total);


        return productRepo.save(productUser);
    }

    // ✅ Update a product-Price
    public void updateAllProductPrices(double newPrice) {

    List<ProductUser> products = productRepo.findAll();

    for (ProductUser p : products) {

        // update price
        p.setProductPrice(newPrice);

        // recalculate total price
        double total = newPrice * p.getProductStock();
        p.setTotalPrice(total);

        productRepo.save(p);
    }
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
