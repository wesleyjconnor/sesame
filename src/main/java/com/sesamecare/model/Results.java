package com.sesamecare.model;

import com.companyX.model.Appointment;
import lombok.Getter;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

public class Results {
    @Getter
    private final List<Practitioner> successful;
    @Getter
    private final Map<String, List<Appointment>> errors;

    @ConstructorProperties({"results", "errors"})
    public Results(
            List<Practitioner> successful,
            Map<String, List<Appointment>> errors
    ) {
        this.successful = successful;
        this.errors = errors;
    }
}
