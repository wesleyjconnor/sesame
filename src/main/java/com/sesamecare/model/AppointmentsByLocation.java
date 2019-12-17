package com.sesamecare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsByLocation {

    private String locationName;

    private Set<Appointment> appointments;
}
