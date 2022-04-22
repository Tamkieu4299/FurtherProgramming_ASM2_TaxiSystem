package com.asm2.taxisys.service;

import com.asm2.taxisys.entity.Customer;
import com.asm2.taxisys.repo.CustomerRepo;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public long saveCustomer(Customer customer){
        Long id = customer.getId();
        List<Customer> customersList = this.getAllCustomers();
        for(Customer c: customersList){
            if(c.getId()==id) {
                System.out.println("Existed customer !");
                return -1;
            }
        }
        sessionFactory.getCurrentSession().save(customer);
        System.out.println("Created customer with the ID: " + customer.getId());
        return customer.getId();
    }

    public void deleteCustomer(Long id){
        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Customer.class, id));
    }

    public long updateCustomer(Customer customer){
        List<Customer> customersList = this.getAllCustomers();
        if(!customersList.contains(customer)){
            System.out.println("Invalid customer !");
            return -1;
        }
        sessionFactory.getCurrentSession().update(customer);
        System.out.println("Updated customer with the ID: " + customer.getId());
        return customer.getId();
    }

    public List<Customer> getAllCustomers(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        return criteria.list();
    }

    public Customer getById(Long id){
        return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
    }
}
