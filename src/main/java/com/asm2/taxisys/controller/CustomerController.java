package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.service.CustomerService;
import com.asm2.taxisys.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public Customer addCustomer(@RequestBody Customer customer){
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

    @RequestMapping(path = "/updateCustomer/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable Long id,@RequestBody Customer updateCustomer){
        return customerService.updateCustomer(updateCustomer);
    }

    @RequestMapping(path = "/allCustomers", method = RequestMethod.GET)
    Page<Customer> getAllCustomers(@RequestParam Optional<Integer> page){
        return customerRepo.findAll(PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "/query/id")
    public Customer getById(@RequestParam long id){
        return customerRepo.findCustomerById(id);
    }

    @GetMapping(path = "query/name")
    public Page<Customer> searchCustomerByName(@RequestParam String name, @RequestParam Optional<Integer> page){
        return customerRepo.findCustomersByName(name, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "query/address")
    public Page<Customer> searchCustomerByAddress(@RequestParam String address, @RequestParam Optional<Integer> page){
        return customerRepo.findCustomersByAddress(address, PageRequest.of(page.orElse(0),5));
    }

    @GetMapping(path = "query/phone")
    public Page<Customer> searchCustomerByPhone(@RequestParam String phone, @RequestParam Optional<Integer> page){
        return customerRepo.findCustomersByPhone(phone, PageRequest.of(page.orElse(0),5));
    }

}
