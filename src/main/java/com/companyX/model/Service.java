package com.companyX.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @NotBlank(message = "Service name is required")
    private String name;

    @DecimalMin(message = "Service price must be greater than zero", value = "0.0", inclusive = false)
    private BigDecimal price;
}
