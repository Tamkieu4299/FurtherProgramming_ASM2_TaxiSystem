package com.asm2.taxisys.repo;

import com.asm2.taxisys.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepo extends PagingAndSortingRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    Booking findBookingById(Long id);

    Page<Booking> findBookingsByStartLocation(String startLocation, Pageable pageable);
    Page<Booking> findBookingsByEndLocation(String endLocation, Pageable pageable);
    Page<Booking> findBookingsByPickTime(String pickTime, Pageable pageable);
    Page<Booking> findBookingsByDropTime(String dropTime, Pageable pageable);
    Page<Booking> findBookingsByTripDistance(Long tripDistance, Pageable pageable);
}
