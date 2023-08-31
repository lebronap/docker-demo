package com.anpeng.docker.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author anpeng
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Docker!";
    }


    @GetMapping("/anpeng")
    public String anpeng() {
        return "Hello, anpeng!";
    }
}