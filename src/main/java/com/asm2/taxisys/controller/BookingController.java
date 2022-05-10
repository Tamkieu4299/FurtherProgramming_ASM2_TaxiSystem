package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Booking;
import com.asm2.taxisys.repo.BookingRepo;
import com.asm2.taxisys.service.BookingService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Booking addBooking(@RequestBody Booking booking){
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
    public Booking updateBooking(@RequestBody Booking booking){
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
    public Iterable<Booking> searchBookingByStartLocation(
            @Spec(path = "startLocation", params = "startLocation", spec = LikeIgnoreCase.class) Specification<Booking> startLocationSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return bookingRepo.findAll(startLocationSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"endLocation"})
    public Iterable<Booking> searchBookingByEndLocation(
            @Spec(path = "endLocation", params = "endLocation", spec = LikeIgnoreCase.class) Specification<Booking> endLocationSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return bookingRepo.findAll(endLocationSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"pickTime"})
    public Iterable<Booking> searchBookingByPickTime(
            @Spec(path = "pickTime", params = "pickTime", spec = LikeIgnoreCase.class) Specification<Booking> pickTimeSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return bookingRepo.findAll(pickTimeSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"dropTime"})
    public Iterable<Booking> searchBookingByDropTime(
            @Spec(path = "dropTime", params = "dropTime", spec = LikeIgnoreCase.class) Specification<Booking> dropTimeSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return bookingRepo.findAll(dropTimeSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"tripDistance"})
    public Iterable<Booking> searchBookingByTripDistance(
            @Spec(path = "tripDistance", params = "tripDistance", spec = LikeIgnoreCase.class) Specification<Booking> tripDistanceSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return bookingRepo.findAll(tripDistanceSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }


    @GetMapping(path = "/statistics")
    public List<Booking> getAllBookingsBetween(@RequestParam("start") String start, @RequestParam("end") String end) throws ParseException {
        Date sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(start);
        Date ed = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(end);
        return bookingService.getAllBookingsBetween(sd, ed);
    }
}
