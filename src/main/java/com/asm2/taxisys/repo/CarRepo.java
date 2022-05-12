package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CarRepo extends PagingAndSortingRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Car findCarById(Long id);

    Page<Car> findCarsByModel(String model, Pageable pageable);
    Page<Car> findCarsByVin(String vin, Pageable pageable);
    Page<Car> findCarsByMake(String make, Pageable pageable);
    Page<Car> findCarsByColor(String color, Pageable pageable);
    Page<Car> findCarsByConvertible(boolean convertible, Pageable pageable);
    Page<Car> findCarsByRating(double rating, Pageable pageable);
    Page<Car> findCarsByLicensePlate(String licensePlate, Pageable pageable);
    Page<Car> findCarsByRatePerKm(double ratePerKm, Pageable pageable);
}
