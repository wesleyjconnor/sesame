package com.companyX.client;


import com.companyX.model.Appointment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@FeignClient(
        name = "companyx-client-api",
        url = "${companyx-client-api}"
)
public interface CompanyXClient {

    @GetMapping(path = "/sesame_programming_test_api")
    @HystrixCommand
    List<Appointment> getAppointments();
}
