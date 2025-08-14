package com.medtech.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/v1/main")
public class UserController {

    @GetMapping
    public String main() {
        return "Hello from user-service";
    }

}
