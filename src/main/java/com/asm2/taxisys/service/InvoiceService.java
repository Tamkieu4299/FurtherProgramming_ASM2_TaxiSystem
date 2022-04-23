package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.InvoiceRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private InvoiceRepo invoiceRepo;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveInvoice(Invoice invoice){
        Long id = invoice.getId();
        List<Invoice> invoicesList = this.getAllInvoices();
        for(Invoice d: invoicesList){
            if(d.getId()==id) {
                System.out.println("Existed invoice !");
                return -1;
            }
        }
        sessionFactory.getCurrentSession().save(invoice);
        System.out.println("Created invoice with the ID: " + invoice.getId());
        return invoice.getId();
    }

    public void deleteInvoice(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Invoice.class, id));
    }

    public long updateInvoice(Invoice invoice){
        List<Invoice> invoicesList = this.getAllInvoices();
        if(!invoicesList.contains(invoice)){
            System.out.println("Invalid invoice !");
            return -1;
        }
        sessionFactory.getCurrentSession().update(invoice);
        System.out.println("Updated invoice with the ID: " + invoice.getId());
        return invoice.getId();
    }

    public List<Invoice> getAllInvoices(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        return criteria.list();
    }

    public Invoice getById(Long id){
        return (Invoice) sessionFactory.getCurrentSession().get(Invoice.class, id);
    }
}
