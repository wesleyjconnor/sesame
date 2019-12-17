package com.sesamecare.service;

import com.companyX.client.CompanyXClient;
import com.companyX.model.Appointment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sesamecare.model.AppointmentsByLocation;
import com.sesamecare.model.Practitioner;
import com.sesamecare.model.Results;
import com.sesamecare.service.util.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyXClient companyXClient;

    @Before
    public void before() {
        Mockito.when(companyXClient.getAppointments()).thenReturn(FileUtil.getTestAppointments());
    }

    @Test
    public void verifyReturnedJson() throws Exception {
        //given

        //when
        MvcResult mvcResult = this.mockMvc.perform(get("/api"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        //then
        String contentAsString = mvcResult.getResponse().getContentAsString();

        Results results = new ObjectMapper().readValue(contentAsString, Results.class);
        verifySuccessful(results);
        verifyFailures(results);
    }

    private void verifyFailures(Results results) {
        Map<String, List<Appointment>> errors = results.getErrors();
        Assertions.assertThat(errors).hasSize(3);

        Assertions.assertThat(errors.get("Location name cannot be null, blank or empty")).hasSize(48);
        Assertions.assertThat(errors.get("Service price must be greater than zero")).hasSize(21);
        Assertions.assertThat(errors.get("Appointment must be in the future")).hasSize(1);
    }

    private void verifySuccessful(Results results) {
        List<Practitioner> successful = results.getSuccessful();

        Assertions.assertThat(getCollect(successful))
                .containsExactlyInAnyOrder( "Hazel Lakin", "Tabitha Hane", "Holden Kemmer", "Katelin Grady", "Russel Wisozk");

        verifyDoctorHasCount(successful, "Hazel Lakin", List.of("1ed06753-358e-4575-814b-a5b018f89b4c", "aa4e0c4a-6597-4cd9-92af-533bc424e489"));
        verifyDoctorHasCount(successful, "Tabitha Hane", List.of("da007f94-6ea2-43d3-a56e-90d98572f051"));
        verifyDoctorHasCount(successful, "Holden Kemmer", List.of("8b74e74a-7348-4af9-9487-3673a9268160"));
        verifyDoctorHasCount(successful, "Katelin Grady", List.of("fe916470-1866-400a-a482-01ca278141fa"));
        verifyDoctorHasCount(successful, "Russel Wisozk", List.of("9fad2506-70c3-4d72-8d18-26716460a714", "2deb1923-13d0-45ff-8bb7-5cce17d72bda"));
    }

    private void verifyDoctorHasCount(List<Practitioner> successful, String doctor, List<String> count) {
        List<String> collect = successful.stream()
                .filter(practitioner -> (practitioner.getFirstName() + " " + practitioner.getLastName()).equals(doctor))
                .map(Practitioner::getAppointmentsByLocation)
                .flatMap(Collection::stream)
                .map(AppointmentsByLocation::getAppointments)
                .flatMap(Collection::stream)
                .map(com.sesamecare.model.Appointment::getAppointmentId)
                .collect(Collectors.toList());
        Assertions.assertThat(collect).containsAll(count);

    }

    private List<String> getCollect(List<Practitioner> successful) {
        return successful.stream()
                .map(success -> success.getFirstName() + " " + success.getLastName())
                .collect(Collectors.toList());
    }
}
