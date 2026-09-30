package com.springsecurity.securitydemo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Security";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Welcome Admin";
    }

    @GetMapping("/payments")
    public String payments() {
        return "Payment details";
    }
}