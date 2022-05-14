package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DriverRepo extends PagingAndSortingRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

//    List<Driver> findAll();
    Driver findDriverById(Long id);
    Page<Driver> findDriversByLicenseNumber(String licenseNumber, Pageable pageable);
    Page<Driver> findDriversByRating(double rating, Pageable pageable);
    Page<Driver> findDriversByPhone(String phone, Pageable pageable);

}
