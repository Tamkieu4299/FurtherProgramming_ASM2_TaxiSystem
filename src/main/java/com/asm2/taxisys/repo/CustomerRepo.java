package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

}
