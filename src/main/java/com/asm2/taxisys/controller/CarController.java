package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//    "make": "Audi",
//            "model": "2022",
//            "color": "black",
//            "convertible": true,
//            "rating": 5.00,
//            "licencePlate": "70H-123",
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

    @PostMapping( "/addCar")
    public Car addCar(@RequestBody Car car){
        System.out.println("Hi");
        return carService.saveCar(car);
//        ResponseEntity<Car> a=returnCar;
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
    public Car updateCar(@PathVariable Long id, @RequestBody Car car){
        return carService.updateCar(car);
    }

//    @RequestMapping(path = "/allCars", method = RequestMethod.GET)
//    Page<Car> getCars(@RequestParam Optional<Integer> page){
//        return carRepo.findAll(PageRequest.of(page.orElse(0),5));
//    }
//
//    @RequestMapping(path = "/getCar/{id}")
//    public Car getById(@PathVariable Long id){
//        return carService.getById(id);
//    }
//
//    @GetMapping(params = {"vin"})
//    public Page<Car> searchCarByVIN(@RequestParam Optional<Integer> page, @Spec(path = "vin", params = "vin", spec = LikeIgnoreCase.class) Specification<Car> vinSpec) {
//        return carRepo.findAll(vinSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"make"})
//    public Page<Car> searchCarByMake(@RequestParam Optional<Integer> page, @Spec(path = "make", params = "make", spec = LikeIgnoreCase.class) Specification<Car> makeSpec) {
//        return carRepo.findAll(makeSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"model"})
//    public Page<Car> searchCarByModel(@RequestParam Optional<Integer> page, @Spec(path = "model", params = "model", spec = LikeIgnoreCase.class) Specification<Car> modelSpec) {
//        return carRepo.findAll(modelSpec,PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"color"})
//    public Page<Car> searchCarByColor(@RequestParam Optional<Integer> page, @Spec(path = "color", params = "color", spec = LikeIgnoreCase.class) Specification<Car> colorSpec) {
//        return carRepo.findAll(colorSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"convertible"})
//    public Page<Car> searchCarByConvertible(@RequestParam Optional<Integer> page, @Spec(path = "convertible", params = "convertible", spec = LikeIgnoreCase.class) Specification<Car> convertibleSpec) {
//        return carRepo.findAll(convertibleSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"rating"})
//    public Page<Car> searchCarByRating(@RequestParam Optional<Integer> page, @Spec(path = "rating", params = "rating", spec = LikeIgnoreCase.class) Specification<Car> ratingSpec) {
//        return carRepo.findAll(ratingSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"licencePlate"})
//    public Page<Car> searchCarByLicencePlate(@RequestParam Optional<Integer> page, @Spec(path = "licencePlate", params = "licencePlate", spec = LikeIgnoreCase.class) Specification<Car> licencePlateSpec) {
//        return carRepo.findAll(licencePlateSpec, PageRequest.of(page.orElse(0),5));
//    }
//
//    @GetMapping(params = {"ratePerKm"})
//    public Page<Car> searchCarByRatePerKm(@RequestParam Optional<Integer> page, @Spec(path = "ratePerKm", params = "ratePerKm", spec = LikeIgnoreCase.class) Specification<Car> ratePerKmSpec) {
//        return carRepo.findAll(ratePerKmSpec, PageRequest.of(page.orElse(0),5));
//    }
}
