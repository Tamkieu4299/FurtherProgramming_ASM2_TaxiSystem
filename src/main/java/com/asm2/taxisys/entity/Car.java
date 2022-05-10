package com.asm2.taxisys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "car")
public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="carId")
    private Long id;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdDate = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private String color;

    @Column
    private Boolean convertible;

    @Column
    private Double rating;


    private String licencePlate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="driverId")
    @JsonManagedReference
    private Driver driver;

    @Column
    private Double ratePerKm;

    @Column
    @CreationTimestamp
    private ZonedDateTime time;
    public Car(){};


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getConvertible() {
        return convertible;
    }

    public void setConvertible(Boolean convertible) {
        this.convertible = convertible;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Double getRatePerKm() {
        return ratePerKm;
    }

    public void setRatePerKm(Double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
