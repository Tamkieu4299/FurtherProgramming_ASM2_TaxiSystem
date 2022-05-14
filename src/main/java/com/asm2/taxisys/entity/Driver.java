package com.asm2.taxisys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.io.Serializable;
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

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


    @OneToOne(mappedBy="driver",cascade = CascadeType.ALL,fetch =FetchType.EAGER)
    @JoinColumn(name="carId")
    @JsonBackReference
    private Car car;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @Column
    @CreationTimestamp
    private ZonedDateTime date;

    public Driver() {};

    public Driver(Long id){
        this.id=id;
    }

    public Driver(Long id, String licenseNumber, String phone, double rating){
        this.id=id;
        this.licenseNumber=licenseNumber;
        this.phone=phone;
        this.rating=rating;
    }

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

