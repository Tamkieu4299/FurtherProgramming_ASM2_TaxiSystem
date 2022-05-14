package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.*;
import com.asm2.taxisys.repo.InvoiceRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private InvoiceRepo invoiceRepo;
    @Autowired
    private BookingService bookingService;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Invoice saveInvoice(Invoice invoice){
        Long id = invoice.getId();
        List<Invoice> invoicesList = this.getAllInvoices();
        for(Invoice d: invoicesList){
            if(d.getId()==id) {
                System.out.println("Existed invoice !");
                return null;
            }
        }
        invoiceRepo.save(invoice);
        System.out.println("Created invoice with the ID: " + invoice.getId());
        return invoice;
    }

    public void deleteInvoice(Long id){
        invoiceRepo.deleteById(id);
    }

    public long updateInvoice(Invoice invoice){
        List<Invoice> invoicesList = this.getAllInvoices();
        for (Invoice i : invoicesList){
            if (i.getId()==invoice.getId()){
                invoiceRepo.save(invoice);
                System.out.println("Updated invoice with the ID: " + invoice.getId());
                return invoice.getId();
            }
        }
        System.out.println("can not update Invoice ");
        return -1;

    }

    public List<Invoice> getAllInvoices(){
        return (List<Invoice>) invoiceRepo.findAll();
    }

    public Invoice getById(Long id){
        return invoiceRepo.findInvoiceById(id);
    }

    public List<Invoice> getAllInvoicesBetween(Date start, Date end) throws ParseException {
        List<Booking> bookings=bookingService.getAllBookingsBetween(start,end);
        List<Invoice> invoices=new ArrayList<>();
        for (Booking booking:bookings){
            invoices.add(booking.getInvoice());
        }
        return invoices;
    }

    public List<Invoice> getAllInvoicesByCustomerBetween(Long customerId, Date start, Date end) throws ParseException {
        List<Booking> bookings=bookingService.getAllBookingsBetween(start,end);
        List<Invoice> invoices=new ArrayList<>();
        for (Booking booking:bookings){
            if (booking.getInvoice().getCustomer().getId().equals(customerId)){
                invoices.add(booking.getInvoice());
            }
        }
        return invoices;
    }

    public List<Invoice> getAllInvoicesByDriverBetween(Long driverId, Date start, Date end) throws ParseException {
        List<Booking> bookings=bookingService.getAllBookingsBetween(start,end);
        List<Invoice> invoices=new ArrayList<>();
        for (Booking booking:bookings){
            if (booking.getInvoice().getDriver().getId().equals(driverId)){
                invoices.add(booking.getInvoice());
            }
        }
        return invoices;
    }
}
