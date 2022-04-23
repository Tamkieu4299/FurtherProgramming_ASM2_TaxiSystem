package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.InvoiceRepo;
import com.asm2.taxisys.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;

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

    @RequestMapping(path = "/allInvoices", method = RequestMethod.GET)
    public List<Invoice> getAllInvoices(){
        return invoiceService.getAllInvoices();
    }

    @RequestMapping(path = "/getInvoice/{id}")
    public Invoice getById(@PathVariable Long id){
        return invoiceService.getById(id);
    }

//    @GetMapping(params = {"startLocation"})
//    public Iterable<Booking> searchBookingByStartLocation(@Spec(path = "startLocation", params = "startLocation", spec = LikeIgnoreCase.class) Specification<Booking> startLocationSpec) {
//        return bookingRepo.findAll(startLocationSpec);
//    }

}
