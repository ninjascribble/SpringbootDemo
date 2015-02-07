package com.ninjascript.springboot.controllers;

import com.ninjascript.springboot.models.Customer;
import com.ninjascript.springboot.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    
    @Autowired
    CustomerRepository customerRepository;
    
    @RequestMapping("/customers")
    public Map<String, List<Customer>> customers(@RequestParam(value="lastName") String lastName)
    {
        Map<String, List<Customer>> map = new HashMap<>();
        map.put("customers", customerRepository.findByLastName(lastName));
        return map;
    }
}
