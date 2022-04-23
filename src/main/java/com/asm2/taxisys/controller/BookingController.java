package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    public BookingController(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @RequestMapping(path = "/addBooking", method = RequestMethod.POST)
    public long addBooking(@RequestBody Booking booking){
        return bookingService.saveBooking(booking);
    }

    @RequestMapping(path = "/deleteBooking/{id}", method = RequestMethod.DELETE)
    public void deleteBooking(@PathVariable Long id){
        try {
            bookingService.deleteBooking(id);
            System.out.println("Deleted booking with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid booking");
        }
    }

    @RequestMapping(path = "/updateBooking", method = RequestMethod.PUT)
    public long updateBooking(@RequestBody Booking booking){
        return bookingService.updateBooking(booking);
    }

    @RequestMapping(path = "/allBookings", method = RequestMethod.GET)
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @RequestMapping(path = "/getBooking/{id}")
    public Booking getById(@PathVariable Long id){
        return bookingService.getById(id);
    }

    @GetMapping(params = {"startLocation"})
    public Iterable<Booking> searchBookingByStartLocation(@Spec(path = "startLocation", params = "startLocation", spec = LikeIgnoreCase.class) Specification<Booking> startLocationSpec) {
        return bookingRepo.findAll(startLocationSpec);
    }

    @GetMapping(params = {"endLocation"})
    public Iterable<Booking> searchBookingByEndLocation(@Spec(path = "endLocation", params = "endLocation", spec = LikeIgnoreCase.class) Specification<Booking> endLocationSpec) {
        return bookingRepo.findAll(endLocationSpec);
    }

    @GetMapping(params = {"pickTime"})
    public Iterable<Booking> searchBookingByPickTime(@Spec(path = "pickTime", params = "pickTime", spec = LikeIgnoreCase.class) Specification<Booking> pickTimeSpec) {
        return bookingRepo.findAll(pickTimeSpec);
    }

    @GetMapping(params = {"dropTime"})
    public Iterable<Booking> searchBookingByDropTime(@Spec(path = "dropTime", params = "dropTime", spec = LikeIgnoreCase.class) Specification<Booking> dropTimeSpec) {
        return bookingRepo.findAll(dropTimeSpec);
    }

    @GetMapping(params = {"tripDistance"})
    public Iterable<Booking> searchBookingByTripDistance(@Spec(path = "tripDistance", params = "tripDistance", spec = LikeIgnoreCase.class) Specification<Booking> tripDistanceSpec) {
        return bookingRepo.findAll(tripDistanceSpec);
    }
}
