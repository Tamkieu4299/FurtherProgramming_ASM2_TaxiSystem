package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.repo.DriverRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class CarService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private DriverRepo driverRepo;
    @Autowired
    private BookingService bookingService;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Car saveCar(Car car){
        carRepo.save(car);
        return car;
    }

    public void deleteCar(Long id){
        carRepo.deleteById(id);
    }

    public Car updateCar(Car car){
        List<Car> carsList = this.getAllCars();
        for (int i=0;i<carsList.size();i+=1){
            if (carsList.get(i).getId()==car.getId()){
                carRepo.save(car);
                return car;
            }
        }
        return null;

    }

    public List<Car> getAllCars(){
        return (List<Car>) carRepo.findAll();
    }

    public Car getById(Long id){
        List<Car> carsList = this.getAllCars();
        for (int i=0;i<carsList.size();i+=1){
            if (carsList.get(i).getId()==id){
                return carsList.get(i);
            }
        }
        return null;
    }
    public List<Car> getFreeCars(String pickTime) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        Date time = format.parse(pickTime);

        List<Driver> allDrivers = (List<Driver>) driverRepo.findAll();
        List<Driver> freeDrivers= this.bookingService.getFreeDrivers(time, allDrivers);
        List<Car> freeCars = new ArrayList<>();
        List<Car> allCars = (List<Car>) carRepo.findAll();

        for(Car car: allCars){
            for (Driver driver:freeDrivers){
                if(car.getDriver().getId().equals(driver.getId()))
                    freeCars.add(car);
            }
        }

        return freeCars;
    }
}
