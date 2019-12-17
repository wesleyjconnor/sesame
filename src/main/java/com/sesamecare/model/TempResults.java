package com.sesamecare.model;

import com.companyX.model.Appointment;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class TempResults {

    /**
     * Map of validation failure reason with the failed Appointments
     */
    @Getter
    private final Map<String, List<Appointment>> invalidResults;
    @Getter
    private final List<Appointment> goodResults;

    public TempResults(
            List<Appointment> goodResults,
            Map<String, List<Appointment>> invalidResults
    ) {
        this.goodResults = goodResults;
        this.invalidResults = invalidResults;
    }
}
