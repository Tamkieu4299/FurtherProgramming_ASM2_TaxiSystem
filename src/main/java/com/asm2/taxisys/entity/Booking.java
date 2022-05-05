package com.asm2.taxisys.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String startLocation;

    @Column
    private String endLocation;

    @Column
    private String pickTime;

    @Column
    private String dropTime;

    @Column
    private Long tripDistance;


    private Long invoiceId;

    @CreationTimestamp
    @Column
    private ZonedDateTime time;

    public Booking() {}

    public Booking(String pickTime, String dropTime, Long invoice){
        this.pickTime = pickTime;
        this.dropTime = dropTime;
        this.invoiceId = invoice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public Long getTripDistance() {
        return tripDistance;
    }

    public void setTripDistance(Long tripDistance) {
        this.tripDistance = tripDistance;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getInvoice() {
        return invoiceId;
    }

    public void setInvoice(Long invoice) {
        this.invoiceId = invoice;
    }
}
