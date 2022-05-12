package com.asm2.taxisys.controller;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarRepo carRepo;

    @Mock
    private CarController carController;

    @MockBean
    private CarService carService;

//    @Before
//    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(carController).build();
//    }

    //    @Test
//    public void givenEmployees_whenGetEmployees_thenStatus200()
//            throws Exception {
//
//        createTestEmployee("bob");
//
//        mvc.perform(get("/api/employees")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//    }
//@Before
//public void setUp() {
//    Mockito.when(carRepo.findAll())
//            .thenReturn((Iterable<Car>) IntStream.range(0, 10)
//                    .mapToObj(i -> new Car((long) i, "a"))
//                    .collect(Collectors.toList()));
//
//
//}

    @Test
    void TestAddCar() throws Exception {
        Car car = new Car();
        when(carService.saveCar(car)).thenReturn(car);
        ResultActions resultActions = mvc.perform(post("/cars/addCar").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"make\": \"Audi\",\n" +
                        "            \"model\": \"2022\",\n" +
                        "            \"color\": \"black\",\n" +
                        "            \"convertible\": true,\n" +
                        "            \"rating\": 5.00,\n" +
                        "            \"licencePlate\": \"70H-123\",\n" +
                        "            \"ratePerKm\": 4.787}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertEquals("Hi", "Hi");
//        System.out.println("hi"+contentAsString);
//         mvc.perform(post("/cars/addCar").contentType(MediaType.APPLICATION_JSON).content("{\n" +
//                        "    \"make\": \"Audi\",\n" +
//                        "            \"model\": \"2022\",\n" +
//                        "            \"color\": \"black\",\n" +
//                        "            \"convertible\": true,\n" +
//                        "            \"rating\": 5.00,\n" +
//                        "            \"licencePlate\": \"70H-123\",\n" +
//                        "            \"ratePerKm\": 4.787}"))
//                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllTrue() throws Exception {
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());

        Page<Car> page = new PageImpl<Car>(allTodos);

        given(carRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        for (Car car : carRepo.findAll(PageRequest.of(0, 5))) {
            System.out.println(car.getId() + car.getModel());
        }
        mvc.perform(get("/admin/cars/allCars?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void getCarbyID() throws Exception {
        Car car = new Car((long) 1, "a");
        given(carService.saveCar(car)).willReturn(car);

        mvc.perform(get("/admin/cars/getCar/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }

    @Test
    void searchCarByModel() throws Exception{
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());
        Page<Car> page = new PageImpl<>(allTodos);
        given(carRepo.findCarsByModel("2022",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/cars/query/model?model=2022&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    public static String asJsonString ( final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addCarTest() throws Exception {
        Car car = new Car((long) 1, "a");
        given(carService.saveCar(car)).willReturn(car);
        mvc.perform(post("/cars/addCar").content(asJsonString(car)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCar() throws Exception{
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());

        Page<Car> page = new PageImpl<Car>(allTodos);

        given(carRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        for (Car car : carRepo.findAll(PageRequest.of(0, 5))) {
            System.out.println(car.getId() + car.getModel());
        }
        mvc.perform(delete("/admin/cars/deleteCar/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updateCarTest() throws Exception {
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());

        Page<Car> page = new PageImpl<Car>(allTodos);

        given(carRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        for (Car car : carRepo.findAll(PageRequest.of(0, 5))) {
            System.out.println(car.getId() + car.getModel());
        }
        mvc.perform(MockMvcRequestBuilders.put("/admin/cars/updateCar/1").content(asJsonString(new Car(1L,"2023"))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void searchCarByRating() throws Exception{
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());
        Page<Car> page = new PageImpl<>(allTodos);
        given(carRepo.findCarsByRating(4.787,PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/cars/query/rating?rating=4.787&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchCarByLicencePlate() throws Exception {
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());
        Page<Car> page = new PageImpl<>(allTodos);
        given(carRepo.findCarsByLicencePlate("70H-123",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/cars/query/licencePlate?licencePlate=70H-123&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchCarByRatePerKm() throws Exception {
        List<Car> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Car((long) i, "123", "Audi", "2022","black",true, (double) 5L,"70H-123",(double)4.787))
                .collect(Collectors.toList());
        Page<Car> page = new PageImpl<>(allTodos);
        given(carRepo.findCarsByRatePerKm(4.787,PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/cars/query/ratePerKm?ratePerKm=4.787&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }
}