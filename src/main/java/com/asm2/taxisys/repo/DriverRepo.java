package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Driver;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepo extends PagingAndSortingRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

//    List<Driver> findAll();
    Driver findDriverById(Long id);

}
