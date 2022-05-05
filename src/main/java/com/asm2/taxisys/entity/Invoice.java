package com.asm2.taxisys.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long customerId;


    private Long driverId;


    private Long bookingId;

    @Column
    private Double totalCharge;

    @Column
    @CreationTimestamp
    private ZonedDateTime time;

    public Invoice() {}

    public Invoice(Long customerId, Long driverId){
        this.customerId=customerId;
        this.driverId=driverId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer() {
        return customerId;
    }

    public void setCustomer(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDriver() {
        return driverId;
    }

    public void setDriver(Long driverId) {
        this.driverId = driverId;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
}
