package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.repo.DriverRepo;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DriverRepo driverRepo;

    @Mock
    private DriverController driverController;

    @MockBean
    private DriverService driverService;

    public static String asJsonString ( final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addDriverTest() throws Exception {
        Driver driver = new Driver((long) 1);
        given(driverService.saveDriver(driver)).willReturn(driver);
        mvc.perform(post("/drivers/addDriver").content(asJsonString(driver)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void addDriverTestFalse() throws Exception {
        Driver driver = new Driver((long) 1);
        given(driverService.saveDriver(driver)).willReturn(driver);
        mvc.perform(post("/drivers/addDriverr").content(asJsonString(driver)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void deleteDriver() throws Exception{
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);

        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/drivers/deleteDriver/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDriverFalse() throws Exception{
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);

        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/drivers/deleteDriverr/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updateDriverTest() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);

        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/drivers/updateDriver/1").content(asJsonString(new Driver(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateDriverTestFalse() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);

        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/drivers/updateDriverr/1").content(asJsonString(new Driver(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllTrue() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/allDrivers?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void testFindAllFalse() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/allDrivers?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void getDriverById() throws Exception {
        Driver driver = new Driver(1L);

        given(driverRepo.findDriverById(1L)).willReturn(driver);

        mvc.perform(get("/drivers/query/id?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void getDriverByIdFalse() throws Exception {
        Driver driver = new Driver(1L);

        given(driverRepo.findDriverById(1L)).willReturn(driver);

        mvc.perform(get("/drivers/query/id?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(0)));
    }

    @Test
    public void searchDriverByLicense() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByLicenseNumber("042127HLBH",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/licenseNumber?licenseNumber=042127HLBH&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchDriverByLicenseFalse() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByLicenseNumber("042127HLBH",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/licenseNumber?licenseNumber=042127HLBH&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(1)));
    }

    @Test
    public void searchDriverByRating() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByRating(5.67,PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/rating?rating=5.67&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchDriverByRatingFalse() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByRating(5.67,PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/rating?rating=5.67&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(1)));
    }

    @Test
    public void searchDriverByPhone() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByPhone("0834086256",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/phone?phone=0834086256&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchDriverByPhoneFalse() throws Exception {
        List<Driver> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Driver((long) i,"042127HLBH", "0834086256",5.67))
                .collect(Collectors.toList());
        Page<Driver> page = new PageImpl<Driver>(allTodos);
        given(driverRepo.findDriversByPhone("0834086256",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/drivers/query/phone?phone=0834086256&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(1)));
    }
}
