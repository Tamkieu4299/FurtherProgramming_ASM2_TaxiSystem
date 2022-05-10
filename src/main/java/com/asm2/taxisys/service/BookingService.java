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
import java.text.ParseException;
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

    public Booking saveBooking(Booking booking){

        bookingRepo.save(booking);
        return booking;
    }

    public void deleteBooking(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Booking.class, id));
    }

    public Booking updateBooking(Booking booking){
        List<Booking> bookingsList = this.getAllBookings();

        for (Booking b:bookingsList){
            if (b.getId()==booking.getId()){
                bookingRepo.save(booking);
                System.out.println("Updated invoice with the ID: " + booking.getId());
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getAllBookings(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Booking.class);
        return criteria.list();
    }

    public Booking getById(Long id){
        return (Booking) sessionFactory.getCurrentSession().get(Booking.class, id);
    }


    public List<Booking> getAllBookingsBetween(Date start, Date end) throws ParseException {
        List<Booking> bookingList= (List<Booking>) bookingRepo.findAll();
        List<Booking> bookings=new ArrayList<>();
        SimpleDateFormat format =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        for (Booking booking:bookingList){
            if (format.parse(booking.getDropTime()).compareTo(start)>=0 &&format.parse(booking.getDropTime()).compareTo(end)<=0){
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public List<Driver> getFreeDrivers(Date time, List<Driver> allDrivers) throws Exception{
        List<Booking> bookings = this.getAllBookings();
        List<Driver> busyDrivers = new ArrayList<>();
        List<Driver> freeDrivers = new ArrayList<>();
        SimpleDateFormat format =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        for(Booking booking: bookings)
            if(format.parse(booking.getDropTime()).compareTo(time)>0 && format.parse(booking.getPickTime()).compareTo(time)<=0 ) {
                Invoice curInvoice = booking.getInvoice();
                Driver busyDriver = curInvoice.getDriver();
                busyDrivers.add(busyDriver);
            }
        for(Driver driver: allDrivers)
            if(!busyDrivers.contains(driver)) freeDrivers.add(driver);

        return freeDrivers;
    }
}
