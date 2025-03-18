package org.example.capstone1_ecommerce.model;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty
    private String id;
    @NotEmpty
    private String productId;
    @NotEmpty
    private String merchantId;

    @Min(value = 10, message = "Stock must start with 10")
    @NotNull
    private int stock;
}
