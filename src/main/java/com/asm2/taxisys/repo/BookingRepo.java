package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Booking;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepo extends PagingAndSortingRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

}
