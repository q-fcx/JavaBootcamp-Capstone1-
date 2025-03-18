package org.example.capstone1_ecommerce.service;


import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Merchant;
import org.example.capstone1_ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {


    ArrayList<Merchant> merchants = new ArrayList<>();

    public Merchant merchantSearchById(String id) {
        for(Merchant merchant : merchants) {
            if(merchant.getId().equals(id)){
                return merchant;
            }
        }
        return null;
    }

    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    public boolean addMerchant(Merchant merchant) {
        for(Merchant m : merchants) {
            if(m.getId().equals(merchant.getId())) {
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String id, Merchant merchant) {
        for(int i = 0; i < merchants.size(); i++) {
            if(merchants.get(i).getId().equals(id)) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id) {
        for(Merchant m : merchants) {
            if(m.getId().equals(id)) {
                merchants.remove(m);
                return true;
            }
        }
        return false;
    }
}

