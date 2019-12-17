package com.sesamecare.service.util;

import com.companyX.model.Appointment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileUtil {

    public static List<Appointment> getTestAppointments() {
        TypeReference<List<Appointment>> typeReference = new TypeReference<>() {
        };
        try {
            Path path = Paths.get(Objects.requireNonNull(FileUtil.class.getClassLoader()
                    .getResource("input.json")).toURI());
            return new ObjectMapper().findAndRegisterModules().readValue(path.toFile(), typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
