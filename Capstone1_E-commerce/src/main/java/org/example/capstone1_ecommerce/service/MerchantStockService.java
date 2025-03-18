package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Merchant;
import org.example.capstone1_ecommerce.model.MerchantStock;
import org.example.capstone1_ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final ProductService productService;
    private final MerchantService merchantService;
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public MerchantStock findMerchantStock(String productId, String merchantId) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId().equals(productId) && stock.getMerchantId().equals(merchantId)) {
                return stock;
            }
        }
        return null;
    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean addMerchantStock(MerchantStock merchantStock) {
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStock.getId())) {
                return false;
            }
        }
        for(Product p : productService.products) {
            if(!p.getId().equals(merchantStock.getProductId())) {
                return false;
            }
                for(Merchant m : merchantService.merchants) {
                    if(!m.getId().equals(merchantStock.getMerchantId())){
                        return false;
                    }
                }
            }
        merchantStocks.add(merchantStock);
        return true;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (MerchantStock c : merchantStocks) {
            if (c.getId().equals(id)) {
                merchantStocks.remove(c);
                return true;
            }
        }
        return false;
    }

    public boolean reduceStock(String id, int amount) {
        for(MerchantStock merchant : merchantStocks) {
            if(merchant.getMerchantId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean addToStock(String productId, String merchantId, int amount) {
        for(MerchantStock m : merchantStocks) {
            if(m.getProductId().equals(productId) && m.getMerchantId().equals(merchantId)) {
                m.setStock(m.getStock() + amount);
                return true;
            }
        }
        return false;
    }
}