package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CarService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CarRepo carRepo;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveCar(Car car){
        try {
            carRepo.save(car);
            return car.getId();
        } catch (Exception e){
            return car.getId();
        }
    }

    public void deleteCar(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Car.class, id));
    }

    public long updateCar(Car car){

        List<Car> carsList = this.getAllCars();
        for (int i=0;i<carsList.size();i+=1){
            if (carsList.get(i).getId()==car.getId()){
                carRepo.save(car);
                return car.getId();
            }
        }
        return -1;

    }
    public long select(Car car,Driver driver){

        List<Car> carsList = this.getAllCars();
        for (int i=0;i<carsList.size();i+=1){
            if (carsList.get(i).getId()==car.getId()){
                car.setDriver(driver);
                sessionFactory.getCurrentSession().persist(car);
                System.out.println("Updated car with the ID: " + car.getId());
                return car.getId();
            }
        }
        return -1;

    }
    public List<Car> getAllCars(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Car.class);
        return criteria.list();
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
}
