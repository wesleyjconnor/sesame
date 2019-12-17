package com.sesamecare.service;

import com.companyX.model.Appointment;
import com.sesamecare.model.Practitioner;
import com.sesamecare.model.TempResults;

import java.util.List;

public interface ValidatingMapper {
    List<Practitioner> map(List<Appointment> appointmentList);

    TempResults validate(List<Appointment> appointmentList);
}
