package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private InvoiceService invoiceService;

    @GetMapping(path = "/view-cars")
    public List<Car> getFreeCars(@RequestParam("pickTime") String pickTime ) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        Date time = format.parse(pickTime);
        List<Driver> allDrivers = driverService.getAllDrivers();
        List<Driver> freeDrivers= this.bookingService.getFreeDrivers(time, allDrivers);
        List<Car> freeCars = new ArrayList<>();

        for(Driver driver: freeDrivers)
            freeCars.add(driver.getCar());

        return freeCars;
    }

    @PostMapping(path = "/book-car")
    public Long bookCar(@RequestParam("customerId") Long customerId, @RequestParam("carId") Long carId, @RequestParam("pickup") String pickup, @RequestParam("drop") String drop ) throws Exception{
        if(!this.getFreeCars(pickup).contains(this.carService.getById(carId))) {
            System.out.println("This car is busy at that time! Please check again");
            return  0L;
        }
        Customer customer = customerService.getById(customerId);
        Driver driver  = carService.getById(carId).getDriver();

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setDriver(driver);

        invoiceController.addInvoice(invoice);
        return invoice.getId();



    }



}
