package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;

//    "make": "Audi",
//            "model": "2022",
//            "color": "black",
//            "convertible": true,
//            "rating": 5.00,
//            "licensePlate": "70H-123",
//            "ratePerKm": 4.787
@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepo carRepo;

    public CarController(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @RequestMapping(path = "/addCar", method = RequestMethod.POST)
    public long addCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @RequestMapping(path = "/deleteCar/{id}", method = RequestMethod.DELETE)
    public void deleteCar(@PathVariable Long id){
        try {
            carService.deleteCar(id);
            System.out.println("Deleted car with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid car");
        }
    }

    @RequestMapping(path = "/updateCar/{id}", method = RequestMethod.PUT)
    public long updateCar(@PathVariable Long id){
        Car car = carService.getById(id);
        return carService.updateCar(car);
    }

    @RequestMapping(path = "/allCars", method = RequestMethod.GET)
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }

    @RequestMapping(path = "/getCar/{id}")
    public Car getById(@PathVariable Long id){
        return carService.getById(id);
    }

    @GetMapping(params = {"make"})
    public Iterable<Car> searchCarByMake(@Spec(path = "make", params = "make", spec = LikeIgnoreCase.class) Specification<Car> makeSpec) {
        return carRepo.findAll(makeSpec);
    }

    @GetMapping(params = {"model"})
    public Iterable<Car> searchCarByModel(@Spec(path = "model", params = "model", spec = LikeIgnoreCase.class) Specification<Car> modelSpec) {
        return carRepo.findAll(modelSpec);
    }

    @GetMapping(params = {"color"})
    public Iterable<Car> searchCarByColor(@Spec(path = "color", params = "color", spec = LikeIgnoreCase.class) Specification<Car> colorSpec) {
        return carRepo.findAll(colorSpec);
    }

    @GetMapping(params = {"convertible"})
    public Iterable<Car> searchCarByConvertible(@Spec(path = "convertible", params = "convertible", spec = LikeIgnoreCase.class) Specification<Car> convertibleSpec) {
        return carRepo.findAll(convertibleSpec);
    }

    @GetMapping(params = {"rating"})
    public Iterable<Car> searchCarByRating(@Spec(path = "rating", params = "rating", spec = LikeIgnoreCase.class) Specification<Car> ratingSpec) {
        return carRepo.findAll(ratingSpec);
    }

    @GetMapping(params = {"licencePlate"})
    public Iterable<Car> searchCarByLicencePlate(@Spec(path = "licencePlate", params = "licencePlate", spec = LikeIgnoreCase.class) Specification<Car> licencePlateSpec) {
        return carRepo.findAll(licencePlateSpec);
    }

    @GetMapping(params = {"ratePerKm"})
    public Iterable<Car> searchCarByRatePerKm(@Spec(path = "ratePerKm", params = "ratePerKm", spec = LikeIgnoreCase.class) Specification<Car> ratePerKmSpec) {
        return carRepo.findAll(ratePerKmSpec);
    }
}
