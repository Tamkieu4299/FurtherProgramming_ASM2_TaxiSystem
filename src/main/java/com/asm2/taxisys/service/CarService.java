package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.repo.CarRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Long id = car.getId();
        List<Car> carsList = this.getAllCars();
        for(Car d: carsList){
            if(d.getId()==id) {
                System.out.println("Existed car !");
                return -1;
            }
        }
        sessionFactory.getCurrentSession().save(car);
        System.out.println("Created car with the ID: " + car.getId());
        return car.getId();
    }

    public void deleteCar(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Car.class, id));
    }

    public long updateCar(Car car){
        List<Car> carsList = this.getAllCars();
        if(!carsList.contains(car)){
            System.out.println("Invalid car !");
            return -1;
        }
        sessionFactory.getCurrentSession().update(car);
        System.out.println("Updated car with the ID: " + car.getId());
        return car.getId();
    }

    public List<Car> getAllCars(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Car.class);
        return criteria.list();
    }

    public Car getById(Long id){
        return (Car) sessionFactory.getCurrentSession().get(Car.class, id);
    }
}
