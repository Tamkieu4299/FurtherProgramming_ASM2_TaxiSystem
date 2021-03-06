package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Invoice;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.BookingService;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    /* Manage Booking */
    @PostMapping(path = "/bookings/addBooking")
    public Booking adminAddBooking(@RequestBody Booking booking){
        return bookingService.saveBooking(booking);
    }

    @RequestMapping(path = "/bookings/deleteBooking/{id}", method = RequestMethod.DELETE)
    public void adminDeleteBooking(@PathVariable Long id){
        try {
            bookingService.deleteBooking(id);
            System.out.println("Deleted booking with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid booking");
        }
    }

    @RequestMapping(path = "/bookings/updateBooking/{id}", method = RequestMethod.PUT)
    public void adminUpdateBooking(@PathVariable Long id, @RequestBody Booking booking){
        try {
            bookingService.updateBooking(booking);
            System.out.println("Updated booking with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid booking");
        }
    }

    @RequestMapping(path = "/bookings/allBookings", method = RequestMethod.GET)
    Page<Booking> adminGetBookings(@RequestParam Optional<Integer> page){
        return bookingRepo.findAll(PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "bookings/query/id")
    public Booking adminSearchBookingById(@RequestParam long id){
        return bookingRepo.findBookingById(id);
    }


    @GetMapping(path = "bookings/query/startLocation")
    public Page<Booking> adminSearchBookingByStartLocation(@RequestParam String startLocation, @RequestParam Optional<Integer> page){
        return bookingRepo.findBookingsByStartLocation(startLocation, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "bookings/query/endLocation")
    public Page<Booking> adminSearchBookingByEndLocation(@RequestParam String endLocation, @RequestParam Optional<Integer> page){
        return bookingRepo.findBookingsByEndLocation(endLocation, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "bookings/query/pickTime")
    public Page<Booking> adminSearchBookingByPickTime(@RequestParam String pickTime, @RequestParam Optional<Integer> page){
        return bookingRepo.findBookingsByPickTime(pickTime, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "bookings/query/dropTime")
    public Page<Booking> adminSearchBookingByDropTime(@RequestParam String dropTime, @RequestParam Optional<Integer> page){
        return bookingRepo.findBookingsByDropTime(dropTime, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "bookings/query/tripDistance")
    public Page<Booking> adminSearchBookingByTripDistance(@RequestParam Long tripDistance, @RequestParam Optional<Integer> page){
        return bookingRepo.findBookingsByTripDistance(tripDistance, PageRequest.of(page.orElse(0),5));
    }

    /* Manage cars */
    @RequestMapping(path = "/cars/addCar", method = RequestMethod.POST)
    public Car adminAddCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @RequestMapping(path = "/cars/deleteCar/{id}", method = RequestMethod.DELETE)
    public void adminDeleteCar(@PathVariable Long id){
        try {
            carService.deleteCar(id);
            System.out.println("Deleted car with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid car");
        }
    }

    @RequestMapping(path = "/cars/updateCar/{id}", method = RequestMethod.PUT)
    public void adminUpdateCar(@PathVariable Long id, @RequestBody Car car){
        try {
            carService.updateCar(car);
            System.out.println("Updated car with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid car");
        }
    }

    @RequestMapping(path = "/cars/allCars", method = RequestMethod.GET)
    Page<Car> adminGetCars(@RequestParam Optional<Integer> page){
        return carRepo.findAll(PageRequest.of(page.orElse(0),5));
    }

    @RequestMapping(path = "/cars/getCar/{id}")
    public Car adminGetCarById(@PathVariable Long id){
        return carService.getById(id);
    }

    @GetMapping(path = "cars/query/id")
    public Car adminSearchCarById(@RequestParam long id){
        return carRepo.findCarById(id);
    }

    @GetMapping(path = "cars/query/vin")
    public Page<Car> adminSearchCarsByVin(@RequestParam String vin, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByVin(vin, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/make")
    public Page<Car> adminSearchCarsByMake(@RequestParam String make, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByMake(make, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/model")
    public Page<Car> adminSearchCarsByModel(@RequestParam String model, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByModel(model, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/color")
    public Page<Car> adminSearchCarsByColor(@RequestParam String color, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByColor(color, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/convertible")
    public Page<Car> adminSearchCarsByConvertible(@RequestParam boolean convertible, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByConvertible(convertible, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/rating")
    public Page<Car> adminSearchCarsByRating(@RequestParam double rating, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByRating(rating, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/licencePlate")
    public Page<Car> adminSearchCarsByLicencePlate(@RequestParam String licencePlate, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByLicencePlate(licencePlate, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "cars/query/ratePerKm")
    public Page<Car> adminSearchCarsByRatePerKm(@RequestParam double ratePerKm, @RequestParam Optional<Integer> page){
        return carRepo.findCarsByRatePerKm(ratePerKm, PageRequest.of(page.orElse(0),5));
    }

    /* Revenue */
    @GetMapping(path = "/total-revenue-period")
    public Map<String, Object> getRevenue(@RequestParam("start") String start,
                                          @RequestParam("end") String end) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(end);

        double revenue = 0;
        List<Invoice> invoices = invoiceService.getAllInvoicesBetween(sd, ed);
        for (Invoice invoice : invoices) {
            revenue += invoice.getTotalCharge();
        }
        Map<String, Object> result = new HashMap<>(3);
        result.put("Begin date", start);
        result.put("End date", end);
        result.put("Revenue", revenue);

        return result;
    }

    @GetMapping(path = "/customer-revenue-period/{id}")
    public Map<String, Object> getRevenueByCustomer(@RequestParam("start") String start,
                                                           @RequestParam("end") String end,
                                                           @PathVariable Long id) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yy HH:mm:ss").parse(end);

        double revenue = 0;
        List<Invoice> invoices = invoiceService.getAllInvoicesByCustomerBetween(id, sd, ed);
        for (Invoice invoice : invoices) {
            revenue += invoice.getTotalCharge();
        }
        Map<String, Object> result = new HashMap<>(4);
        result.put("Customer's ID", id);
        result.put("Begin date", start);
        result.put("End date", end);
        result.put("Revenue", revenue);

        return result;
    }

    @GetMapping(path = "/driver-revenue-period/{id}")
    public Map<String, Object> getRevenueByDriver(@RequestParam("start") String start,
                                                    @RequestParam("end") String end,
                                                    @PathVariable Long id) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end);

        double revenue = 0;
        List<Invoice> invoices = invoiceService.getAllInvoicesByDriverBetween(id, sd, ed);
        for (Invoice invoice : invoices) {
            revenue += invoice.getTotalCharge();
        }
        Map<String, Object> result = new HashMap<>(4);
        result.put("Driver's ID", id);
        result.put("Begin date", start);
        result.put("End date", end);
        result.put("Revenue", revenue);

        return result;
    }

    @GetMapping(path = "/car-use-month")
    public Map<String, Integer> usesOfCarInMonth(@RequestParam("month") String month,
                                                @RequestParam("year") String year) throws ParseException{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", Locale.ENGLISH);
        cal.setTime(format.parse(year+"/"+month));

        List<Invoice> invoices = invoiceService.getAllInvoices();

        Map<String, Set<ZonedDateTime>> checkRepeatDate = new HashMap<>();
        Map<String, Integer> result = new HashMap<>();
        for(Invoice invoice: invoices){
            String carLicensePlate =carRepo.findCarById(invoice.getDriver().getCar().getId()).getLicencePlate();
            if(!result.containsKey(carLicensePlate)) {
                result.put(carLicensePlate, 1);
                Set<ZonedDateTime> days = new HashSet<>();
                days.add(invoice.getTime());
                checkRepeatDate.put(carLicensePlate, days);
            }
            else
                if(checkRepeatDate.get(carLicensePlate).add(invoice.getTime())) result.put(carLicensePlate, result.get(carLicensePlate)+1);
        }
        return result;
    }
}
