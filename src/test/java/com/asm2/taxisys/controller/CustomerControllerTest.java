
package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.repo.CustomerRepo;
import com.asm2.taxisys.repo.DriverRepo;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.CustomerService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepo customerRepo;

    @Mock
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    public static String asJsonString ( final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addCustomerTest() throws Exception {
        Customer customer = new Customer((long) 1);
        given(customerService.saveCustomer(customer)).willReturn(customer);
        mvc.perform(post("/customers/addCustomer").content(asJsonString(customer)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void addCustomerTestFalse() throws Exception {
        Customer customer = new Customer((long) 1);
        given(customerService.saveCustomer(customer)).willReturn(customer);
        mvc.perform(post("/customers/addCustomerr").content(asJsonString(customer)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void deleteCustomer() throws Exception{
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);

        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/customers/deleteCustomer/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerFalse() throws Exception{
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);

        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/customers/deleteCustomer/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomerTest() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);

        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/customers/updateCustomer/1").content(asJsonString(new Customer(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomerTestFalse() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);

        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/customers/updateCustomerr/1").content(asJsonString(new Customer(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFindAllTrue() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/customers/allCustomers?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void testFindAllFalse() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/customers/allCustomer?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getCustomerById() throws Exception {
        Customer customer = new Customer(1L);

        given(customerRepo.findCustomerById(1L)).willReturn(customer);

        mvc.perform(get("/customers/query/id?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void getCustomerByIdFalse() throws Exception {
        Customer customer = new Customer(1L);

        given(customerRepo.findCustomerById(1L)).willReturn(customer);

        String result =mvc.perform(get("/customers/query/id?id=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    public void searchCustomerByName() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByName("Tam",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/customers/query/name?name=Tam&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchCustomerByNameFalse() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByName("Kieu Tam",PageRequest.of(0, 5))).willReturn(page);
        String result=mvc.perform(get("/customers/query/name?name=Tam&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    public void searchCustomerByAddress() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByAddress("d7",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/customers/query/address?address=d7&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchCustomerByAddressFalse() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByAddress("d7",PageRequest.of(0, 5))).willReturn(page);
        String result=mvc.perform(get("/customers/query/address?address=d10&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

    @Test
    public void searchCustomerByPhone() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByPhone("0834086256",PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/customers/query/phone?phone=0834086256&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content",hasSize(5)));
    }

    @Test
    public void searchCustomerByPhoneFalse() throws Exception {
        List<Customer> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Customer((long) i,"Kieu Tam", "d7","0834086256"))
                .collect(Collectors.toList());
        Page<Customer> page = new PageImpl<Customer>(allTodos);
        given(customerRepo.findCustomersByPhone("0834086256",PageRequest.of(0, 5))).willReturn(page);
        String result=mvc.perform(get("/customers/query/phone?phone=08340862561&page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }
}