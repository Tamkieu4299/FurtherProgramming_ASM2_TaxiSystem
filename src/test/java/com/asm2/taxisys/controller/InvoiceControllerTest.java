package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.repo.DriverRepo;
import com.asm2.taxisys.repo.InvoiceRepo;
import com.asm2.taxisys.service.DriverService;
import com.asm2.taxisys.service.InvoiceService;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class InvoiceControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private InvoiceRepo invoiceRepo;

    @MockBean
    private BookingRepo bookingRepo;

    @Mock
    private InvoiceController invoiceController;

    @MockBean
    private InvoiceService invoiceService;

    public static String asJsonString ( final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addInvoiceTest() throws Exception {
        Invoice invoice = new Invoice((long) 1);
        given(invoiceService.saveInvoice(invoice)).willReturn(invoice);
        mvc.perform(post("/invoices/addInvoice").content(asJsonString(invoice)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void addInvoiceTestFail() throws Exception {
        Invoice invoice = new Invoice((long) 1);
        given(invoiceService.saveInvoice(invoice)).willReturn(invoice);
        mvc.perform(post("/invoices/addInvoicec").content(asJsonString(invoice)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
//                .andExpect(jsonPath("$.id", is(1L)));
    }

    @Test
    void deleteInvoice() throws Exception{
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/invoices/deleteInvoice/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteInvoiceFalse() throws Exception{
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(delete("/invoices/deleteInvoicec/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateInvoiceTest() throws Exception {
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/invoices/updateInvoice/1").content(asJsonString(new Invoice(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateInvoiceTestFalse() throws Exception {
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(MockMvcRequestBuilders.put("/invoices/updateInvocice/1").content(asJsonString(new Invoice(1L))).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFindAllTrue() throws Exception {
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/invoices/allInvoices?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    public void testFindAllFalse() throws Exception {
        List<Invoice> allTodos = IntStream.range(0, 5)
                .mapToObj(i -> new Invoice((long)i))
                .collect(Collectors.toList());
        Page<Invoice> page = new PageImpl<Invoice>(allTodos);
        given(invoiceRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        mvc.perform(get("/invoices/allInvoice?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getInvoiceById() throws Exception {
        Invoice invoice = new Invoice(1L);

        given(invoiceRepo.findInvoiceById(1L)).willReturn(invoice);

        mvc.perform(get("/invoices/query/id?id=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
    }

    @Test
    public void getInvoiceByIdFalse() throws Exception {
        Invoice invoice = new Invoice(1L);

        given(invoiceRepo.findInvoiceById(1L)).willReturn(invoice);

        String result=mvc.perform(get("/invoices/query/id?id=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(result,"");
    }

//    public void getAllInvoicesBetween() throws Exception{
//        List<Booking> allTodos = IntStream.range(0, 5)
//                .mapToObj(i -> new Booking((long) i, "HCM", "HN", "05/14/2022 18:00:00","05/14/2022 19:00:00",10L))
//                .collect(Collectors.toList());
//        Booking b1 = allTodos.get(0);
//        Invoice invoice = new Invoice(1L);
//        b1.setInvoice(invoice);
//
//        List<Booking> bookings=new ArrayList<>();
//        SimpleDateFormat format =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        for (Booking booking:allTodos){
//            if (format.parse(booking.getDropTime()).compareTo("05/14/2022 19:00:00")>=0 &&format.parse(booking.getDropTime()).compareTo(end)<=0){
//                bookings.add(booking);
//            }
//        }
//
//        Page<Booking> page = new PageImpl<Booking>(allTodos);
//        given(bookingRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
//
//    }
}
