package com.sesamecare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Practitioner {
    private String firstName;
    private String lastName;
    private List<AppointmentsByLocation> appointmentsByLocation;
}
