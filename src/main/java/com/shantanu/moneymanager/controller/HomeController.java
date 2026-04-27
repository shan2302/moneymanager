package com.shantanu.moneymanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Because we are using RestApi
@RequestMapping({"/status","/health"}) //We have to Map Java Method with URI
public class HomeController {
    @GetMapping
    public String healthCheck() {
        return "Application is running";
    }
}
