package com.cp.kku.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.kku.demo.model.Customer;
import com.cp.kku.demo.service.CustomerService;
import com.cp.kku.demo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/user/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    
    

    @PostMapping("/user/customers")
    public Customer addNewCustomer(@RequestBody Customer customer) {
        // กำหนดให้ผู้ใช้ปัจจุบันเป็นเจ้าของข้อมูลลูกค้า
        String currentUsername = getCurrentUsername();
        customer.setUser(userService.findByUsername(currentUsername).get());  // สมมติว่า Customer มี field 'owner'
        Customer savedCustomer = customerService.saveCustomer(customer);
        return savedCustomer;
    }

    @PutMapping("/user/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer newCustomer,
                                                   @PathVariable("id") int id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // ตรวจสอบว่าใครเป็นเจ้าของข้อมูลลูกค้า
        if (!isCurrentUserOwner(existingCustomer)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 Forbidden
        }

        // ดำเนินการอัปเดต
        Customer updatedCustomer = customerService.updateCustomer(id, newCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/user/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") int id) {
        Customer existingCustomer = customerService.getCustomerById(id);
        if (existingCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // ตรวจสอบว่าใครเป็นเจ้าของข้อมูลลูกค้า
        if (!isCurrentUserOwner(existingCustomer)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 403 Forbidden
        }

        // ดำเนินการลบ
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // เมธอดช่วยเหลือเพื่อดึงชื่อผู้ใช้ปัจจุบัน
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // ดึงชื่อผู้ใช้จาก authentication object
    }

    // เมธอดช่วยเหลือเพื่อตรวจสอบว่าใครเป็นเจ้าของข้อมูลลูกค้า
    private boolean isCurrentUserOwner(Customer customer) {
        String currentUsername = getCurrentUsername();
        return customer.getUser().getUsername().equals(currentUsername);  // สมมติว่า Customer มีเมธอด getOwner()
    }
}
