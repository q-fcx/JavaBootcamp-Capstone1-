package org.example.capstone1_ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty
    private String id;
    @NotEmpty
    @Min(value = 3, message = "Name must be more than 3 characters")
    private String name;

}
