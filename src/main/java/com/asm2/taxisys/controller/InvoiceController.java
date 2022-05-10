package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.InvoiceRepo;
import com.asm2.taxisys.service.InvoiceService;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepo invoiceRepo;

    public InvoiceController(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @RequestMapping(path = "/addInvoice", method = RequestMethod.POST)
    public long addInvoice(@RequestBody Invoice invoice){
        return invoiceService.saveInvoice(invoice);
    }

    @RequestMapping(path = "/deleteInvoice/{id}", method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable Long id){
        try {
            invoiceService.deleteInvoice(id);
            System.out.println("Deleted invoice with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid invoice");
        }
    }

    @RequestMapping(path = "/updateInvoice", method = RequestMethod.PUT)
    public long updateInvoice(@RequestBody Invoice invoice){
        return invoiceService.updateInvoice(invoice);
    }

//    @RequestMapping(path = "/allInvoices", method = RequestMethod.GET)
//    public List<Invoice> getAllInvoices(){
//        return invoiceService.getAllInvoices();
//    }

    @GetMapping(path = "/allInvoices")
    Page<Invoice> getAllInvoices(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return invoiceRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @RequestMapping(path = "/getInvoice/{id}")
    public Invoice getById(@PathVariable Long id){
        return invoiceService.getById(id);
    }


    @GetMapping(path = "/in-period")
    public List<Invoice> getAllInvoicesBetween(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end);
        return invoiceService.getAllInvoicesBetween(sd, ed);
    }

    @GetMapping(path = "/query/by-customer/{id}")
    public List<Invoice> getAllSaleInvoicesByCustomerBetween(@PathVariable Long id, @RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end);
        return invoiceService.getAllInvoicesByCustomerBetween(id, sd, ed);
    }

    @GetMapping(path = "/query/by-driver/{id}")
    public List<Invoice> getAllSaleInvoicesByDriverBetween(@PathVariable Long id, @RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end);
        return invoiceService.getAllInvoicesByDriverBetween(id, sd, ed);
    }
}
