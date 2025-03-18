package org.example.capstone1_ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Merchant;
import org.example.capstone1_ecommerce.service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchant() {
        return ResponseEntity.ok(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = merchantService.addMerchant(merchant);
        if(isAdded) {
            return ResponseEntity.accepted().body("Merchant Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if(isUpdated) {
            return ResponseEntity.accepted().body("Merchant updated successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if(isDeleted) {
            return ResponseEntity.accepted().body("Merchant deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }

}

