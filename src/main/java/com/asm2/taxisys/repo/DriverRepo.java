package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Driver;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DriverRepo extends PagingAndSortingRepository<Driver, Long>, JpaSpecificationExecutor<Driver> {

}
