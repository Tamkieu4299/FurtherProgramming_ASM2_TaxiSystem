package com.asm2.taxisys.controller;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
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

    @RequestMapping(path = "/updateDriver/{id}", method = RequestMethod.POST)
    public Driver updateDriver(@PathVariable Long id,@RequestBody Driver updatedDriver){
        return driverService.updateDriver(updatedDriver);
    }

    @RequestMapping(path = "/allDrivers", method = RequestMethod.GET)
    Page<Driver> getDrivers(@RequestParam Optional<Integer> page){
        return driverRepo.findAll(PageRequest.of(page.orElse(0),5));
    }

    @RequestMapping(path = "/getDriver/{id}")
    public Driver getById(@PathVariable Long id){
        return driverService.getById(id);
    }

    @GetMapping(params = {"licenseNumber"})
    Page<Driver> searchDriverByLicense(@RequestParam Optional<Integer> page, @Spec(path = "licenseNumber", params = "licenseNumber", spec = LikeIgnoreCase.class) Specification<Driver> licenseSpec){
        return driverRepo.findAll(licenseSpec,PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(params = {"rating"})
    public Page<Driver> searchDriverByRating(@RequestParam Optional<Integer> page, @Spec(path = "rating", params = "rating",  spec = LikeIgnoreCase.class) Specification<Driver> ratingSpec) {
        return driverRepo.findAll(ratingSpec, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(params = {"phone"})
    public Page<Driver> searchDriverByPhone(@RequestParam Optional<Integer> page,@Spec(path = "phone", params = "phone",  spec = LikeIgnoreCase.class) Specification<Driver> phoneSpec) {
        return driverRepo.findAll(phoneSpec, PageRequest.of(page.orElse(0),5));
    }

    @PostMapping(path = "/select-car/{id}")
    public void selectCar(@RequestParam("carId") Long carId, @PathVariable("id") Long id){
        if(carService.getById(carId).getDriver()==null) {
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

