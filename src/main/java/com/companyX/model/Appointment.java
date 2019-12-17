package com.companyX.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sesamecare.util.CustomLocalDateTimeDeserializer;
import com.sesamecare.util.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private String id;

    @Positive(message = "Duration must be more than zero")
    private int durationInMinutes;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime time;

    @Valid
    private Doctor doctor;
    @Valid
    private Service service;
    @Valid
    private Location location;

}
