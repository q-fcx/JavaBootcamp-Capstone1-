package org.example.capstone1_ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.MerchantStock;
import org.example.capstone1_ecommerce.service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity getMerchantStocks() {
        return ResponseEntity.ok(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = merchantStockService.addMerchantStock(merchantStock);
        if(isAdded) {
            return ResponseEntity.accepted().body("Merchant Stock Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if(isUpdated) {
            return ResponseEntity.accepted().body("Merchant Stock updated");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);
        if(isDeleted) {
            return ResponseEntity.accepted().body("Merchant Stock deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }

    @PostMapping("/stock/{merchantId}/{productId}/{amount}")
    public ResponseEntity addToStock(@PathVariable String merchantId, @PathVariable String productId, @PathVariable int amount) {
        boolean isAdded = merchantStockService.addToStock(productId, merchantId, amount);
        if(isAdded){
            return ResponseEntity.ok("Product amount is added and stock is updated");
        }
        return ResponseEntity.badRequest().body("Invalid Id check: merchant Id or product Id");
    }
}
