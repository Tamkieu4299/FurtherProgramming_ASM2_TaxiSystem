package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.DriverService;
import com.asm2.taxisys.repo.DriverRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    public Driver addDriver(@RequestBody Driver driver){
        return driverService.saveDriver(driver);
    }

    @RequestMapping(path = "/deleteDriver/{id}", method = RequestMethod.DELETE)
    public void deleteDriver(@PathVariable Long id){
        try {
            driverService.deleteDriver(id);
            System.out.println("Deleted driver with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid driver");
        }
    }

    @RequestMapping(path = "/updateDriver/{id}", method = RequestMethod.PUT)
    public Driver updateDriver(@PathVariable Long id,@RequestBody Driver updatedDriver){
        return driverService.updateDriver(updatedDriver);
    }

    @RequestMapping(path = "/allDrivers", method = RequestMethod.GET)
    Page<Driver> getDrivers(@RequestParam Optional<Integer> page){
        return driverRepo.findAll(PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "/query/id")
    public Driver getById(@RequestParam long id){
        return driverRepo.findDriverById(id);
    }

    @GetMapping(path = "query/licenseNumber")
    public Page<Driver> searchDriverByLicense(@RequestParam String licenseNumber, @RequestParam Optional<Integer> page){
        return driverRepo.findDriversByLicenseNumber(licenseNumber, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "query/rating")
    public Page<Driver> searchDriverByRating(@RequestParam double rating, @RequestParam Optional<Integer> page){
        return driverRepo.findDriversByRating(rating, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "query/phone")
    public Page<Driver> searchDriverByPhone(@RequestParam String phone, @RequestParam Optional<Integer> page){
        return driverRepo.findDriversByPhone(phone, PageRequest.of(page.orElse(0),5));
    }

    @PostMapping(path = "/select-car/{id}")
    public Car selectCar(@RequestParam("carId") Long carId, @PathVariable("id") Long id){
        if(carService.getById(carId).getDriver()==null) {
            Car car = carService.getById(carId);
            Driver driver = driverService.getById(id);
            driver.setCar(car);
            car.setDriver(driver);
            carService.updateCar(car);
            driverService.updateDriver(driver);
            return car;
        }
        System.out.println("This car has been chosen !");
        return null;
    }
}

