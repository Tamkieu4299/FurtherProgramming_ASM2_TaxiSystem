package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Customer findCustomerById(long Id);
    Page<Customer> findCustomersByName(String name, Pageable pageable);
    Page<Customer> findCustomersByAddress(String address, Pageable pageable);
    Page<Customer> findCustomersByPhone(String phone, Pageable pageable);
}
