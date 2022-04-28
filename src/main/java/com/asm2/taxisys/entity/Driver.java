package com.asm2.taxisys.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver{

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String licenseNumber;

    @Column
    private String phone;

    @Column
    private Double rating;

//    @JoinColumn(name = "id")
    @OneToOne
    private Car car;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
    private List<Invoice> invoices;

    @Column
    @CreationTimestamp
    private ZonedDateTime date;

    public Driver() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }


}

