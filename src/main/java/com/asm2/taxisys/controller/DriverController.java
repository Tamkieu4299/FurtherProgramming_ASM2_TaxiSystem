package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.DriverService;
import com.asm2.taxisys.repo.DriverRepo;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;
import java.util.Optional;

//"licenseNumber": "abc123",
//        "phone": "0834086256",
//        "rating": 5.00
@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private CarService carService;

    public DriverController(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @RequestMapping(path = "/addDriver", method = RequestMethod.POST)
    public Driver addDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @RequestMapping(path = "/deleteDriver/{id}", method = RequestMethod.DELETE)
    public void deleteDriver(@PathVariable Long id) {
        try {
            driverService.deleteDriver(id);
            System.out.println("Deleted driver with ID: " + id);
        } catch (Exception e) {
            System.out.println("Invalid driver");
        }
    }

    @RequestMapping(path = "/updateDriver/{id}", method = RequestMethod.POST)
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver updatedDriver) {
        return driverService.updateDriver(updatedDriver);
    }

    //    @RequestMapping(path = "/allDrivers", method = RequestMethod.GET)
//    public List<Driver> getAllDrivers(){
//        return driverService.getAllDrivers();
//    }
    @GetMapping(path = "/allDrivers")
    Page<Driver> getAllDrivers(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return driverRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @RequestMapping(path = "/getDriver/{id}")
    public Driver getById(@PathVariable Long id) {
        return driverService.getById(id);
    }

    @GetMapping(params = {"licenseNumber"})
    public Iterable<Driver> searchDriverByLicense(
            @Spec(path = "licenseNumber", params = "licenseNumber", spec = LikeIgnoreCase.class) Specification<Driver> licenseSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return driverRepo.findAll(licenseSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"rating"})
    public Iterable<Driver> searchDriverByRating(
            @Spec(path = "rating", params = "rating", spec = LikeIgnoreCase.class) Specification<Driver> ratingSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return driverRepo.findAll(ratingSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"phone"})
    public Iterable<Driver> searchDriverByPhone(
            @Spec(path = "phone", params = "phone", spec = LikeIgnoreCase.class) Specification<Driver> phoneSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return driverRepo.findAll(phoneSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @PostMapping(path = "/select-car/{id}")
    public void selectCar(@RequestParam("carId") Long carId, @PathVariable("id") Long id) {
        if (carService.getById(carId).getDriver() == null) {
            Car car = carService.getById(carId);
            Driver driver = driverService.getById(id);
            driver.setCar(car);
            car.setDriver(driver);
            carService.updateCar(car);
            driverService.updateDriver(driver);
            return;
        }
        System.out.println("This car has been choosen !");
    }
}
