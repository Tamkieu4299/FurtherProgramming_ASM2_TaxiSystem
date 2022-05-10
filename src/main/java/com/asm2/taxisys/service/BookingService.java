package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.repo.DriverRepo;
import com.asm2.taxisys.repo.InvoiceRepo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZonedDateTime;
import java.util.List;

@Transactional
@Service
public class BookingService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveBooking(Booking booking){
        Long id = booking.getId();
        List<Booking> bookingsList = this.getAllBookings();
        for(Booking d: bookingsList){
            if(d.getId()==id) {
                System.out.println("Existed booking !");
                return -1;
            }
        }
        bookingRepo.save(booking);
        System.out.println("Created booking with the ID: " + booking.getId());
        return booking.getId();
    }

    public void deleteBooking(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Booking.class, id));
    }

    public long updateBooking(Booking booking){
        List<Booking> bookingsList = this.getAllBookings();

        for (Booking b:bookingsList){
            if (b.getId()==booking.getId()){
                bookingRepo.save(booking);
                System.out.println("Updated invoice with the ID: " + booking.getId());
                return booking.getId();
            }
        }
        return -1;
    }

    public List<Booking> getAllBookings(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        return criteria.list();
    }

    public Booking getById(Long id){
        return (Booking) sessionFactory.getCurrentSession().get(Booking.class, id);
    }

    public List<Booking> getAllBookingsOnDate(Date onDate) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Booking> cr = cb.createQuery(Booking.class);
        Root<Booking> root = cr.from(Booking.class);
        final ZoneId zone = ZoneId.systemDefault();
        cr.select(root).where(cb.equal(root.get("time"), ZonedDateTime.ofInstant(onDate.toInstant(), zone)));
        return session.createQuery(cr).getResultList();
    }

    public List<Booking> getAllBookingsBetween(Date start, Date end) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Booking> cr = cb.createQuery(Booking.class);
        Root<Booking> root = cr.from(Booking.class);
        final ZoneId zone = ZoneId.systemDefault();

        Date endFinal = new Date(end.getTime() + (1000 * 60 * 60 * 24));
        cr.select(root).where(cb.between(root.get("time"), ZonedDateTime.ofInstant(start.toInstant(), zone), ZonedDateTime.ofInstant(endFinal.toInstant(), zone)));
        return session.createQuery(cr).getResultList();
    }

    public List<Driver> getFreeDrivers(Date time, List<Driver> allDrivers) throws Exception{
        List<Booking> bookings = this.getAllBookings();
        List<Driver> busyDrivers = new ArrayList<>();
        List<Driver> freeDrivers = new ArrayList<>();
        SimpleDateFormat format =new SimpleDateFormat("MM/dd/yy HH:mm:ss");

        for(Booking booking: bookings)
            if(format.parse(booking.getDropTime()).compareTo(time)>0) {
                Invoice curInvoice = invoiceRepo.findInvoiceById(booking.getInvoice());
                Driver busyDriver = driverRepo.findDriverById(curInvoice.getDriver());
                busyDrivers.add(busyDriver);
            }
        for(Driver driver: allDrivers)
            if(!busyDrivers.contains(driver)) freeDrivers.add(driver);

        return freeDrivers;
    }
}
