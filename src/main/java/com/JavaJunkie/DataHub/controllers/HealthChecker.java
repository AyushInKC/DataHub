package com.JavaJunkie.DataHub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkHealth")
public class HealthChecker {

    @GetMapping
    public String getHealth(){
        return "The Health of the Application is OK!!!";
    }
}
