package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.*;
import com.asm2.taxisys.repo.InvoiceRepo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        for (int i = 0; i < invoicesList.size(); i += 1) {
            if (invoicesList.get(i).getId().equals(invoice.getId())) {
                invoicesList.set(i,invoice);
                sessionFactory.getCurrentSession().merge(invoicesList.get(i));
                System.out.println("Updated invoice with the ID: " + invoice.getId());
                return invoice.getId();
            }
        }

        return -1;
    }

    public List<Invoice> getAllInvoices(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        return criteria.list();
    }

    public Invoice getById(Long id){
        return (Invoice) sessionFactory.getCurrentSession().get(Invoice.class, id);
    }

//    public List<Invoice> getAllInvoicesOnDate(Date onDate) {
////        Session session = sessionFactory.openSession();
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        CriteriaQuery<Invoice> cr = cb.createQuery(Invoice.class);
////        Root<Invoice> root = cr.from(Invoice.class);
////        final ZoneId zone = ZoneId.systemDefault();
////        cr.select(root).where(cb.equal(root.get("time"), ZonedDateTime.ofInstant(onDate.toInstant(), zone)));
////        return session.createQuery(cr).getResultList();
//        return  Collections.<Invoice>emptyList();
//    }
//
//    public List<Invoice> getAllInvoicesBetween(Date start, Date end) {
////        Session session = sessionFactory.openSession();
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        CriteriaQuery<Invoice> cr = cb.createQuery(Invoice.class);
////        Root<Invoice> root = cr.from(Invoice.class);
////        final ZoneId zone = ZoneId.systemDefault();
////        Date endFinal = new Date(end.getTime() + (1000 * 60 * 60 * 24));
////        cr.select(root).where(cb.between(root.get("time"), ZonedDateTime.ofInstant(start.toInstant(), zone), ZonedDateTime.ofInstant(endFinal.toInstant(), zone)));
////        return session.createQuery(cr).getResultList();
////        String hql = "SELECT E.firstName FROM Employee E";
////        Query query = session.createQuery(hql);
////        List results = query.list();
//
//        return  Collections.<Invoice>emptyList();
//    }
//
//    public List<Invoice> getAllInvoicesByCustomerBetween(Long customerId, Date start, Date end) {
////        Session session = sessionFactory.openSession();
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        CriteriaQuery<Invoice> cr = cb.createQuery(Invoice.class);
////        Root<Invoice> root = cr.from(Invoice.class);
////        Join<Invoice, Customer> customerInvoice = root.join(Invoice_.customer);
////        final ZoneId zone = ZoneId.systemDefault();
////        Date endFinal = new Date(end.getTime() + (1000 * 60 * 60 * 24));
////        List<Predicate> predicates = new ArrayList<>();
////        predicates.add(cb.equal(customerInvoice.get(Customer_.id), customerId));
////        predicates.add(cb.between(root.get("time"), ZonedDateTime.ofInstant(start.toInstant(), zone), ZonedDateTime.ofInstant(endFinal.toInstant(), zone)));
////        cr.select(root).where(predicates.toArray(new Predicate[]{}));
////        return session.createQuery(cr).getResultList();
//
//        return  Collections.<Invoice>emptyList();
//    }
//
//    public List<Invoice> getAllInvoicesByDriverBetween(Long driverId, Date start, Date end) {
////        Session session = sessionFactory.openSession();
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        CriteriaQuery<Invoice> cr = cb.createQuery(Invoice.class);
////        Root<Invoice> root = cr.from(Invoice.class);
////        Join<Invoice, Driver> driverInvoice = root.join(Invoice_.driver);
////        final ZoneId zone = ZoneId.systemDefault();
////        Date endFinal = new Date(end.getTime() + (1000 * 60 * 60 * 24));
////        List<Predicate> predicates = new ArrayList<>();
////        predicates.add(cb.equal(driverInvoice.get(Driver_.id), driverId));
////        predicates.add(cb.between(root.get("time"), ZonedDateTime.ofInstant(start.toInstant(), zone), ZonedDateTime.ofInstant(endFinal.toInstant(), zone)));
////        cr.select(root).where(predicates.toArray(new Predicate[]{}));
////        return session.createQuery(cr).getResultList();
//        List<Invoice> a = Collections.<Invoice>emptyList();
//        return a;
//    }
}
