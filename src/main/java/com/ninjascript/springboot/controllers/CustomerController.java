package com.ninjascript.springboot.controllers;

import com.ninjascript.springboot.models.Customer;
import com.ninjascript.springboot.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    
    @Autowired
    CustomerRepository customerRepository;
    
    @RequestMapping("/customers")
    public List<Customer> customers(@RequestParam(value="lastName") String lastName)
    {
        return customerRepository.findByLastName(lastName);
    }
}
