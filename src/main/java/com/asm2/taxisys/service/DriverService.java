package com.asm2.taxisys.service;
import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
import com.asm2.taxisys.repo.DriverRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DriverService {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarService carService;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public long saveDriver(Driver driver){
        String licenseNumber = driver.getLicenseNumber();
        List<Driver> driversList = (List<Driver>) driverRepo.findAll();
        for(Driver d: driversList){
            if(d.getLicenseNumber().equals(licenseNumber)) {
                System.out.println("Existed driver !");
                return -1;
            }
        }
        driverRepo.save(driver);
        System.out.println("Created driver with the ID: " + driver.getId());
        return driver.getId();
    }

    public void deleteDriver(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Driver.class, id));
    }


    public Long selectCar(Long driverID, Long carID){
        Driver driver = driverRepo.findDriverById(driverID);
        Car car = carRepo.findCarById(carID);
        if (car.getDriver() ==  null){
            car.setDriver(driver);
            driver.setCar(car);
            driverRepo.save(driver);
            carRepo.save(car);
            return driverID;
        }
        System.out.println("Car is already selected");
        return driverID;
    }

    public long updateDriver(Driver driver){
        List<Driver> driversList = this.getAllDrivers();
        for (int i=0;i<driversList.size();i+=1){
            if (driversList.get(i).getId()==driver.getId()){
//                System.out.println(driver.getCar().getId());

                driverRepo.save(driver);
                return driver.getId();
            }
        }
        return -1;
    }
    public long select(Driver driver,Car car){
        List<Driver> driversList = this.getAllDrivers();
        for (int i=0;i<driversList.size();i+=1){
            if (driversList.get(i).getId()==driver.getId()){
                Driver a=this.getById(driver.getId());
                Car b=carService.getById(car.getId());
                a.setCar(b);
//                String hql = "UPDATE Driver set car = :car "  +
//                        "WHERE id = :driverId";
//                Query query = sessionFactory.getCurrentSession().createQuery(hql);
//                query.setParameter("car", driver.getCar());
//                query.setParameter("driverId", driver.getId());
//                int result = query.executeUpdate();
//                System.out.println("Rows affected: " + result);
                sessionFactory.getCurrentSession().evict(driver);
                sessionFactory.getCurrentSession().update(driver);

//                sessionFactory.getCurrentSession().merge(driver);
//                sessionFactory.getCurrentSession().evict(driver);
//                System.out.println("Updated car with the ID: " + driver.getId());
                return driver.getId();
            }
        }
        return -1;
    }

    public List<Driver> getAllDrivers(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Driver.class);
        return criteria.list();
    }

    public Driver getById(Long id){
        List<Driver> driversList = this.getAllDrivers();
        for (int i=0;i<driversList.size();i+=1){
            if (driversList.get(i).getId()==id){
                return driversList.get(i);
            }
        }
        return null;
    }
}
