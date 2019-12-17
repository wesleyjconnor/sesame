package com.sesamecare.service;

import com.companyX.model.Appointment;
import com.sesamecare.model.AppointmentService;
import com.sesamecare.model.AppointmentsByLocation;
import com.sesamecare.model.Practitioner;
import com.sesamecare.model.TempResults;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Service
public class CompanyXValidatingMapper implements ValidatingMapper {

    private final Validator validator;

    public CompanyXValidatingMapper() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Map external Doctors and Appointments to local Practitioners and AppointmentsByLocation
     */
    @Override
    public List<Practitioner> map(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .collect(groupingBy(com.companyX.model.Appointment::getDoctor))
                .entrySet().stream()
                .map(doctorListEntry -> new Practitioner(
                        doctorListEntry.getKey().getFirstName(),
                        doctorListEntry.getKey().getLastName(),
                        getAppointmentsByLocations(doctorListEntry.getValue())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Use validator to find bad Appointments and separate them
     */
    @Override
    public TempResults validate(List<Appointment> appointmentList) {

        Map<String, List<Appointment>> failedEntries = new HashMap<>();
        List<Appointment> goodResults = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            Set<ConstraintViolation<Appointment>> validate = validator.validate(appointment);
            if (validate.isEmpty()) {

                if (invalidDate(appointment)) {
                    failedEntries.computeIfAbsent("Appointment must be in the future", k -> new ArrayList<>()).add(appointment);
                } else {
                    goodResults.add(appointment);
                }
            } else {
                for (ConstraintViolation<Appointment> violation : validate) {
                    failedEntries.computeIfAbsent(violation.getMessage(), k -> new ArrayList<>()).add(appointment);
                }
            }
        }
        return new TempResults(goodResults, failedEntries);
    }

    /**
     * Compare if appointment time 'in timezone' is *after* 'now in timezone'
     */
    private boolean invalidDate(
            Appointment appointment
    ) {
        if (appointment.getTime() == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now(ZoneId.of(appointment.getLocation().getTimeZoneCode()));
        LocalDateTime apptTime = appointment.getTime().atZone(ZoneId.of(appointment.getLocation().getTimeZoneCode())).toLocalDateTime();
        return apptTime.isAfter(now);
    }

    /**
     * Turn appointments into AppointmentsByLocation then group then by location
     */
    private List<AppointmentsByLocation> getAppointmentsByLocations(List<Appointment> appointments) {

        return appointments.stream()
                .map(appointment -> new AppointmentsByLocation(appointment.getLocation().getName(), getAppointments(appointment)))
                .collect(groupingBy(AppointmentsByLocation::getLocationName, toSet()))
                .entrySet().stream()
                .map(key -> new AppointmentsByLocation(key.getKey(), key.getValue().stream()
                        .map(AppointmentsByLocation::getAppointments)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()
                        ))
                )
                .collect(Collectors.toList())
                ;
    }

    private Set<com.sesamecare.model.Appointment> getAppointments(com.companyX.model.Appointment appointment) {
        return Set.of(new com.sesamecare.model.Appointment(
                        appointment.getId(),
                        appointment.getTime(),
                        new AppointmentService(appointment.getService().getName(), appointment.getService().getPrice())
                )
        );
    }
}
