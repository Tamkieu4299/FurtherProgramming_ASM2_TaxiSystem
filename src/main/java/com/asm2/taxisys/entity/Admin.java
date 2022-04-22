package com.asm2.taxisys.entity;
import java.util.ArrayList;

public class Admin {
    private ArrayList<Car> cars;
    private ArrayList<Booking> bookings;
    private ArrayList<Invoice> invoices;

    public Admin(){
        this.cars = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public Admin(ArrayList<Car> cars, ArrayList<Booking> bookings, ArrayList<Invoice> invoices) {
        this.cars = cars;
        this.bookings = bookings;
        this.invoices = invoices;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }
}
