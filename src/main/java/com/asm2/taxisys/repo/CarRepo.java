package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CarRepo extends PagingAndSortingRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Car findCarById(Long id);
}
