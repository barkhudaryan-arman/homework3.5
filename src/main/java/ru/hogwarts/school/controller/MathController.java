package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    @GetMapping("/optimized-sum")
    public int getOptimizedSum() {
        int n = 1_000_000;
        return n*(n + 1)/2;
    }
}
