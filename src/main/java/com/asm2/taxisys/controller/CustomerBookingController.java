package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.*;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.repo.InvoiceRepo;
import com.asm2.taxisys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.ZonedDateTime;
import java.time.LocalTime;
@RestController
@RequestMapping("/customer-booking")
public class CustomerBookingController {

    @Autowired
    private CarService carService;

    @Autowired
    private InvoiceController invoiceController;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private InvoiceRepo invoiceRepo;
    @Autowired
    private BookingRepo bookingRepo;

    @GetMapping(path = "/view-cars")
    public List<Car> getFreeCars(@RequestParam("pickTime") String pickTime ) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date time = format.parse(pickTime);
        ZonedDateTime newPickTime=ZonedDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());

//        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime low = ZonedDateTime.now().with(LocalTime.of(0, 0, 0));
        ZonedDateTime high = ZonedDateTime.now().with(LocalTime.of(23, 59, 59));

        List<Driver> allDrivers = driverService.getAllDrivers();
        List<Driver> freeDrivers= this.bookingService.getFreeDrivers(time, allDrivers);
        List<Car> freeCars = new ArrayList<>();
        if (newPickTime.compareTo(low)>=0 && newPickTime.compareTo(high)<0){
            for(Driver driver: freeDrivers)
                if(driver.getCar()!=null)
                    freeCars.add(driver.getCar());
        }

        return freeCars;
    }
    @PostMapping(path = "/book-car")
    public Long bookCar(@RequestParam("customerId") Long customerId, @RequestParam("carId") Long carId, @RequestParam("pickup") String pickup, @RequestParam("drop") String drop,@RequestParam("totalCharge") double totalCharge,@RequestParam("startLocation") String startLocation,@RequestParam("endLocation") String endLocation,@RequestParam("tripDistance") Long tripDistance ) throws Exception{
        boolean canSelected=false;
//        DateTimeFormat format = new DateTimeFormat("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
//        if(format.format(now))
        for (Car car:this.getFreeCars(pickup)){
            if(car.getId().equals(carId)) {
                canSelected=true;
                break;
            }
        }
        if (!canSelected){
            System.out.println("This car is busy at that time! Please check again");
            return  0L;
        }

//        Customer customer = customerService.getById(customerId);
        Long driverId  = carRepo.findCarById(carId).getDriver().getId();
        Booking booking=new Booking();
        Invoice invoice = new Invoice();
        invoice.setCustomer(customerService.getById(customerId));
        invoice.setDriver(driverService.getById(driverId));
        invoice.setTotalCharge(totalCharge);
        invoiceRepo.save(invoice);
        booking.setDropTime(drop);
        booking.setEndLocation(endLocation);
        booking.setPickTime(pickup);
        booking.setInvoice(invoice);
        booking.setStartLocation(startLocation);
        booking.setTripDistance(tripDistance);
        bookingRepo.save(booking);

//        invoice.setTime(pickup);
//        invoice.get
//        invoiceController.addInvoice(invoice);

        return invoice.getId();
    }

}
