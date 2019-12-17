package com.companyX.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @NotBlank(message = "Location name cannot be null, blank or empty")
    private String name;
    @NotBlank(message = "Timezone code cannot be null, blank or empty")
    private String timeZoneCode;
}
