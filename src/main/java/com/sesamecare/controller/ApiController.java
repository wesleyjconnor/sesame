package com.sesamecare.controller;

import com.companyX.client.CompanyXClient;
import com.companyX.model.Appointment;
import com.sesamecare.model.Practitioner;
import com.sesamecare.model.Results;
import com.sesamecare.model.TempResults;
import com.sesamecare.service.ValidatingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ApiController {

    private final CompanyXClient companyXClient;
    private final ValidatingMapper validatingMapper;

    @GetMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public Results getResults(
    ) {
        List<Appointment> appointments = companyXClient.getAppointments();
        TempResults validate = validatingMapper.validate(appointments);
        List<Practitioner> goodResults = validatingMapper.map(validate.getGoodResults());
        return new Results(goodResults, validate.getInvalidResults());
    }

}
