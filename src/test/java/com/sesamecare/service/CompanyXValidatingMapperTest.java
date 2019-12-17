package com.sesamecare.service;

import com.companyX.model.Appointment;
import com.companyX.model.Doctor;
import com.companyX.model.Location;
import com.companyX.model.Service;
import com.sesamecare.service.util.FileUtil;
import com.sesamecare.util.CustomLocalDateTimeDeserializer;
import com.sesamecare.model.TempResults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CompanyXValidatingMapperTest {

    private CompanyXValidatingMapper companyXValidatingMapper;

    @Before
    public void before() {
        this.companyXValidatingMapper = new CompanyXValidatingMapper();
    }

    @Test
    public void validateMappingOfValuesIsSuccessful() {

        //given
        List<Appointment> appointments = FileUtil.getTestAppointments();

        //hen
        TempResults tempResults = companyXValidatingMapper.validate(appointments);


        //then
        Assertions.assertThat(tempResults.getGoodResults()).hasSize(7);
        Assertions.assertThat(tempResults.getInvalidResults()).hasSize(3);

        assertValidResults(tempResults.getGoodResults());
    }

    private void assertValidResults(List<Appointment> goodResults) {
        goodResults.forEach(appointment -> {
            Assertions.assertThat(getOneResult().get(appointment.getId()))
                    .isEqualToComparingFieldByField(appointment);
        });
    }

    private Map<String, Appointment> getOneResult() {

        return Map.of(
                "2deb1923-13d0-45ff-8bb7-5cce17d72bda", new Appointment("2deb1923-13d0-45ff-8bb7-5cce17d72bda", 12, fromDate("2021-12-16 03:54:00"), new Doctor("Russel", "Wisozk"), new Service("Podiatry", new BigDecimal(2491)), new Location("Wichita rehabilitation center", "America/Chicago")),
                "9fad2506-70c3-4d72-8d18-26716460a714", new Appointment("9fad2506-70c3-4d72-8d18-26716460a714", 10, fromDate("2021-12-20 03:54:00"), new Doctor("Russel", "Wisozk"), new Service("Enema", new BigDecimal(1939)), new Location("Wichita rehabilitation center", "America/Chicago")),
                "aa4e0c4a-6597-4cd9-92af-533bc424e489", new Appointment("aa4e0c4a-6597-4cd9-92af-533bc424e489", 12, fromDate("2021-12-20 02:59:00"), new Doctor("Hazel", "Lakin"), new Service("Annual check up", new BigDecimal(100)), new Location("Wichita rehabilitation center", "America/Chicago")),
                "fe916470-1866-400a-a482-01ca278141fa", new Appointment("fe916470-1866-400a-a482-01ca278141fa", 11, fromDate("2021-12-20 10:09:00"), new Doctor("Katelin", "Grady"), new Service("Sports physical", new BigDecimal(8675)), new Location("New York City Clinic", "America/New_York")),
                "8b74e74a-7348-4af9-9487-3673a9268160", new Appointment("8b74e74a-7348-4af9-9487-3673a9268160", 12, fromDate("2021-12-20 03:58:00"), new Doctor("Holden", "Kemmer"), new Service("Enema", new BigDecimal(1939)), new Location("Wichita rehabilitation center", "America/Chicago")),
                "da007f94-6ea2-43d3-a56e-90d98572f051", new Appointment("da007f94-6ea2-43d3-a56e-90d98572f051", 13, fromDate("2019-12-21 05:07:00"), new Doctor("Tabitha", "Hane"), new Service("X-Ray", new BigDecimal(9427)), new Location("New York City Clinic", "America/New_York")),
                "1ed06753-358e-4575-814b-a5b018f89b4c", new Appointment("1ed06753-358e-4575-814b-a5b018f89b4c", 6, fromDate("2021-12-20 02:59:00"), new Doctor("Hazel", "Lakin"), new Service("Enema", new BigDecimal(1939)), new Location("Wichita rehabilitation center", "America/Chicago"))
        );

    }

    private LocalDateTime fromDate(String s) {
        return LocalDateTime.from(new CustomLocalDateTimeDeserializer().deserialize(s));
    }

}
