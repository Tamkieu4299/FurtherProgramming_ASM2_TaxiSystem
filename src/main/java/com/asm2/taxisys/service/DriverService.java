package com.asm2.taxisys.service;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.repo.CarRepo;
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

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarService carService;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Driver saveDriver(Driver driver){
        String licenseNumber = driver.getLicenseNumber();
        List<Driver> driversList = (List<Driver>) driverRepo.findAll();
        for(Driver d: driversList){
            if(d.getLicenseNumber().equals(licenseNumber)) {
                System.out.println("Existed driver !");
                return null;
            }
        }
        driverRepo.save(driver);
        System.out.println("Created driver with the ID: " + driver.getId());
        return driver;
    }

    public void deleteDriver(Long id){
        driverRepo.deleteById(id);
    }

    public Driver updateDriver(Driver driver){
        List<Driver> driversList = this.getAllDrivers();
        for (int i=0;i<driversList.size();i+=1){
            if (driversList.get(i).getId()==driver.getId()){
                driverRepo.save(driver);
                return driver;
            }
        }
        return null;
    }

    public List<Driver> getAllDrivers(){
        return (List<Driver>) driverRepo.findAll();
    }

    public Driver getById(Long id){
        List<Driver> driversList = this.getAllDrivers();
        for(int i=0;i<driversList.size();i+=1){
            if (driversList.get(i).getId()==id){
                return driversList.get(i);
            }
        }
        return null;
    }
}
