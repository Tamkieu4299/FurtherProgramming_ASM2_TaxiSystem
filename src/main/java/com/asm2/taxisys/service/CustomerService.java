package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.repo.CustomerRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CustomerService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CustomerRepo customerRepo;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Customer saveCustomer(Customer customer){

        customerRepo.save(customer);
        return customer;
    }

    public void deleteCustomer(Long id){
        customerRepo.deleteById(id);
    }

    public Customer updateCustomer(Customer customer){
        List<Customer> customersList = this.getAllCustomers();
        for (int i = 0; i < customersList.size(); i += 1) {
            if (customersList.get(i).getId().equals(customer.getId())) {
                customerRepo.save(customer);
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers(){
        return (List<Customer>) customerRepo.findAll();
    }

    public Customer getById(Long id){
        return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
    }
}
