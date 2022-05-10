package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
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

    @RequestMapping(path = "/addCar", method = RequestMethod.POST)
    public Car addCar(@RequestBody Car car){
        System.out.println(car.getLicencePlate());
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
    public Car updateCar(@PathVariable Long id, @RequestBody Car car){
//        Car car = carService.getById(id);
        System.out.println(car.getMake());
        return carService.updateCar(car);
    }

//    @RequestMapping(path = "/allCars", method = RequestMethod.GET)
//    public List<Car> getAllCars(){
//        return carService.getAllCars();
//    }

    @GetMapping(path = "/allCars")
    Page<Car> getAllCars(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return carRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @RequestMapping(path = "/getCar/{id}")
    public Car getById(@PathVariable Long id){
        return carService.getById(id);
    }

    @GetMapping(params = {"make"})
    public Iterable<Car> searchCarByMake(
            @Spec(path = "make", params = "make", spec = LikeIgnoreCase.class) Specification<Car> makeSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(makeSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"model"})
    public Iterable<Car> searchCarByModel(
            @Spec(path = "model", params = "model", spec = LikeIgnoreCase.class) Specification<Car> modelSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(modelSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @GetMapping(params = {"color"})
    public Iterable<Car> searchCarByColor(
            @Spec(path = "color", params = "color", spec = LikeIgnoreCase.class) Specification<Car> colorSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(colorSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"convertible"})
    public Iterable<Car> searchCarByConvertible(
            @Spec(path = "convertible", params = "convertible", spec = LikeIgnoreCase.class) Specification<Car> convertibleSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(convertibleSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"rating"})
    public Iterable<Car> searchCarByRating(
            @Spec(path = "rating", params = "rating", spec = LikeIgnoreCase.class) Specification<Car> ratingSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(ratingSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"licencePlate"})
    public Iterable<Car> searchCarByLicencePlate(
            @Spec(path = "licencePlate", params = "licencePlate", spec = LikeIgnoreCase.class) Specification<Car> licencePlateSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(licencePlateSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }

    @GetMapping(params = {"ratePerKm"})
    public Iterable<Car> searchCarByRatePerKm(
            @Spec(path = "ratePerKm", params = "ratePerKm", spec = LikeIgnoreCase.class) Specification<Car> ratePerKmSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return carRepo.findAll(ratePerKmSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                ));
    }
}
