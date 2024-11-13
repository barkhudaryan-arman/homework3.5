package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/info")
@RestController
public class InfoConrtoller {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String port() {
        return serverPort;
    }

}
