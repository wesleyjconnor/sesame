package com.companyX.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @NotBlank(message = "First name cannot be null, blank or empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be null, blank or empty")
    private String lastName;
}
