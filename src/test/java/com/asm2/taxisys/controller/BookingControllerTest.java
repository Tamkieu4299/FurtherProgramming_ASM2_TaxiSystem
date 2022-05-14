package com.asm2.taxisys.controller;

import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.BookingService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingRepo bookingRepo;

    @Mock
    private  AdminController adminController;

    @MockBean
    private BookingService bookingService;

//    @Test
//    void TestAddCar() throws Exception {
//        Booking booking = new Booking();
//        when(bookingService.saveBooking(booking)).thenReturn(booking);
//        ResultActions resultActions = mvc.perform(post("/cars/addCar").contentType(MediaType.APPLICATION_JSON).content("{\n" +
//                        "    \"make\": \"Audi\",\n" +
//                        "            \"model\": \"2022\",\n" +
//                        "            \"color\": \"black\",\n" +
//                        "            \"convertible\": true,\n" +
//                        "            \"rating\": 5.00,\n" +
//                        "            \"licencePlate\": \"70H-123\",\n" +
//                        "            \"ratePerKm\": 4.787}"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk());
//        MvcResult result = resultActions.andReturn();
//        String contentAsString = result.getResponse().getContentAsString();
//        assertEquals("Hi", "Hi");
//    }

    @Test
    public void testFindAllTrue() throws Exception {
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());

        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/allBookings?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void testFindAllFalse() throws Exception {
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());

        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/allBooking?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void getBookingById() throws Exception {
        Booking booking = new Booking(1L);

        given(bookingRepo.findBookingById(1L)).willReturn(booking);

        mvc.perform(get("/admin/bookings/query/id?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void getBookingByIdFalse() throws Exception {
        Booking booking = new Booking(1L);

        given(bookingRepo.findBookingById(1L)).willReturn(booking);

        String result = mvc.perform(get("/admin/bookings/query/id?id=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    void searchBookingByStartLocation() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByStartLocation("HCM",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/query/startLocation?startLocation=HCM&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchBookingByStartLocationFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByStartLocation("HCM",PageRequest.of(0, 5))).willReturn(page);
        String result =mvc.perform(get("/admin/bookings/query/startLocation?startLocation=HN&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    void searchBookingByEndLocation() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByEndLocation("HN",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/query/endLocation?endLocation=HN&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchBookingByEndLocationFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByEndLocation("HN",PageRequest.of(0, 5))).willReturn(page);
        String result =mvc.perform(get("/admin/bookings/query/endLocation?endLocation=HCM&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    void searchBookingByPickTime() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByPickTime("05/14/2022 18:00:00",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/query/pickTime?pickTime=05/14/2022 18:00:00&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchBookingByPickTimeFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByPickTime("05/14/2022 18:00:00",PageRequest.of(0, 5))).willReturn(page);
        String result =mvc.perform(get("/admin/bookings/query/pickTime?pickTime=05/14/2022 18:00:01&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    void searchBookingByDropTime() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByDropTime("05/14/2022 19:00:00",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/query/dropTime?dropTime=05/14/2022 19:00:00&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchBookingByDropTimeFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByDropTime("05/14/2022 19:00:00",PageRequest.of(0, 5))).willReturn(page);
        String result =mvc.perform(get("/admin/bookings/query/dropTime?dropTime=05/14/2022 19:00:01&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    void searchBookingByTripDistance() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByTripDistance(10L,PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/admin/bookings/query/tripDistance?tripDistance=10&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    void searchBookingByTripDistanceFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);
        given(bookingRepo.findBookingsByTripDistance(10L,PageRequest.of(0, 5))).willReturn(page);
        String result=mvc.perform(get("/admin/bookings/query/tripDistance?tripDistance=11&page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    public static String asJsonString ( final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addBookingTest() throws Exception {
        Booking booking = new Booking((long) 1);
        given(bookingService.saveBooking(booking)).willReturn(booking);

        mvc.perform(post("/admin/bookings/addBooking").content(asJsonString(booking)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void addBookingTestFalse() throws Exception {

        Booking booking = new Booking((long) 1);
//        System.out.println(asJsonString(booking));
        given(bookingService.saveBooking(booking)).willReturn(booking);
        mvc.perform(post("/admin/bookings/addBooking").content("\"id\":1,\"startLocation\":null,\"endLocation\":null,\"pickTime\":null,\"dropTime\":null,\"tripDistance\":null,\"invoice\":null,\"time\":null").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void deleteBooking() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/admin/bookings/deleteBooking/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBookingFalse() throws Exception{
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/admin/bookings/deleteBookig/6").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateBookingTest() throws Exception {
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/admin/bookings/updateBooking/1").content(asJsonString(new Car(1L,"2023"))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateBookingTestFalse() throws Exception {
        List<Booking> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
                .collect(Collectors.toList());
        Page<Booking> page = new PageImpl<Booking>(allTodos);

        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/admin/bookings/updateBookng/6").content(asJsonString(new Car(1L,"2023"))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}