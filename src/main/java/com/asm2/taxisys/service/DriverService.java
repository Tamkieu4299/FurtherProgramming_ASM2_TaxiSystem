package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.DriverRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DriverService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DriverRepo driverRepo;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveDriver(Driver driver){
        Long id = driver.getId();
        List<Driver> driversList = this.getAllDrivers();
        for(Driver d: driversList){
            if(d.getId()==id) {
                System.out.println("Existed driver !");
                return -1;
            }
        }
        sessionFactory.getCurrentSession().save(driver);
        System.out.println("Created driver with the ID: " + driver.getId());
        return driver.getId();
    }

    public void deleteDriver(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Driver.class, id));
    }

    public long updateDriver(Driver driver){
        List<Driver> driversList = this.getAllDrivers();
        if(!driversList.contains(driver)){
            System.out.println("Invalid driver !");
            return -1;
        }
        sessionFactory.getCurrentSession().update(driver);
        System.out.println("Updated driver with the ID: " + driver.getId());
        return driver.getId();
    }

    public List<Driver> getAllDrivers(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class);
        return criteria.list();
    }

    public Driver getById(Long id){
        return (Driver) sessionFactory.getCurrentSession().get(Driver.class, id);
    }
}
