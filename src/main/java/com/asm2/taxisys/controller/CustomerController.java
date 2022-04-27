package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.service.CustomerService;
import com.asm2.taxisys.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @RequestMapping(path = "/addCustomer", method = RequestMethod.POST)
    public long addCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @RequestMapping(path = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomer(id);
            System.out.println("Deleted customer with ID: "+id);
        } catch (Exception e) {
            System.out.println("Invalid customer");
        }
    }

    @RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT)
    public long updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }

    @RequestMapping(path = "/allCustomers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @RequestMapping(path = "/getCustomer/{id}")
    public Customer getById(@PathVariable Long id){
        return customerService.getById(id);
    }

    @GetMapping(params = {"name"})
    public Iterable<Customer> searchCustomerByName(@Spec(path = "name", params = "name", spec = LikeIgnoreCase.class) Specification<Customer> nameSpec) {
        return customerRepo.findAll(nameSpec);
    }

    @GetMapping(params = {"address"})
    public Iterable<Customer> searchCustomerByAddress(@Spec(path = "address", params = "address",  spec = LikeIgnoreCase.class) Specification<Customer> addressSpec) {
        return customerRepo.findAll(addressSpec);
    }

    @GetMapping(params = {"phone"})
    public Iterable<Customer> searchCustomerByPhone(@Spec(path = "phone", params = "phone",  spec = LikeIgnoreCase.class) Specification<Customer> phoneSpec) {
        return customerRepo.findAll(phoneSpec);
    }







}
