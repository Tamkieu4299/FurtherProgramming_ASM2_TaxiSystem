package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
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
        return carService.updateCar(car);
    }
}
