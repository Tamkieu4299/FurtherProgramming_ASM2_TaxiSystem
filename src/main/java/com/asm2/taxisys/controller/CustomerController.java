package com.asm2.taxisys.controller;

import com.asm2.taxisys.entity.Car;
import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.entity.Driver;
import com.asm2.taxisys.service.BookingService;
import com.asm2.taxisys.service.CarService;
import com.asm2.taxisys.service.CustomerService;
import com.asm2.taxisys.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

import java.util.List;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CarService carService;
    @Autowired
    private BookingService bookingService;

    public CustomerController(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @RequestMapping(path = "/addCustomer", method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @RequestMapping(path = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            System.out.println("Deleted customer with ID: " + id);
        } catch (Exception e) {
            System.out.println("Invalid customer");
        }
    }

    @RequestMapping(path = "/updateCustomer", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    //    @RequestMapping(path = "/allCustomers", method = RequestMethod.GET)
//    public List<Customer> getAllCustomers(){
//        return customerService.getAllCustomers();
//    }
    @GetMapping(path = "/allCustomers")
    Page<Customer> getCustomers(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return customerRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @RequestMapping(path = "/getCustomer/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

//    @GetMapping("/getCustomer/{id}")
//    @ResponseBody
//    public Iterable<Customer> getById(
//            @Spec(path = "id", params = "id", spec = LikeIgnoreCase.class) Specification<Customer> id,
//            @RequestParam Optional<Integer> page,
//            @RequestParam Optional<String> sortBy
//                                      ) {
//        return customerRepo.findAll(id,
//                PageRequest.of(
//                        page.orElse(0),
//                        5,
//                        Sort.Direction.ASC, sortBy.orElse("id")
//                )
//        );
//    }

    @GetMapping(params = {"name"})
    public Iterable<Customer> searchCustomerByName(
            @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class) Specification<Customer> nameSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return customerRepo.findAll(nameSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @GetMapping(params = {"address"})
    public Iterable<Customer> searchCustomerByAddress(
            @Spec(path = "address", params = "address", spec = LikeIgnoreCase.class) Specification<Customer> addressSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return customerRepo.findAll(addressSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

    @GetMapping(params = {"phone"})
    public Iterable<Customer> searchCustomerByPhone(
            @Spec(path = "phone", params = "phone", spec = LikeIgnoreCase.class) Specification<Customer> phoneSpec,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy) {
        return customerRepo.findAll(
                phoneSpec,
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

}
