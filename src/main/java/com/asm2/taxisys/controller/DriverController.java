package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.DriverService;
import com.asm2.taxisys.repo.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;

//"licenseNumber": "abc123",
//        "phone": "0834086256",
//        "rating": 5.00
@RestController
@RequestMapping("/drivers")
public class DriverController {

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
    public long addDriver(@RequestBody Driver driver){
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
    public long updateDriver(@PathVariable Long id){
        Driver driver = driverService.getById(id);
        return driverService.updateDriver(driver);
    }

    @RequestMapping(path = "/allDrivers", method = RequestMethod.GET)
    public List<Driver> getAllDrivers(){
        return driverService.getAllDrivers();
    }

    @RequestMapping(path = "/getDriver/{id}")
    public Driver getById(@PathVariable Long id){
        return driverService.getById(id);
    }

    @GetMapping(params = {"licenseNumber"})
    public Iterable<Driver> searchDriverByLicense(@Spec(path = "licenseNumber", params = "licenseNumber", spec = LikeIgnoreCase.class) Specification<Driver> licenseSpec) {
        return driverRepo.findAll(licenseSpec);
    }

    @GetMapping(params = {"rating"})
    public Iterable<Driver> searchDriverByRating(@Spec(path = "rating", params = "rating",  spec = LikeIgnoreCase.class) Specification<Driver> ratingSpec) {
        return driverRepo.findAll(ratingSpec);
    }

    @GetMapping(params = {"phone"})
    public Iterable<Driver> searchDriverByPhone(@Spec(path = "phone", params = "phone",  spec = LikeIgnoreCase.class) Specification<Driver> phoneSpec) {
        return driverRepo.findAll(phoneSpec);
    }

    @PostMapping(path = "/select-car/{id}")
    public void selectCar(@RequestParam("carId") Long carId, @PathVariable Long id){
        if(carService.getById(carId).getDriverId()==null && driverService.getById(id).getCarId()==null){
            Car car=carService.getById(carId);
            Driver driver=driverService.getById(id);
            driver.setCarId(carId);
            car.setDriverId(id);

            driverService.updateDriver(driver);
            carService.updateCar(car);

            System.out.println("Selected car for driver !");
            return;
        }
        else if(driverService.getById(id).getCarId()!=null) {
            System.out.println("This driver already had car !");
            return;
        }
        System.out.println("This car has been choosen !");
    }
}
