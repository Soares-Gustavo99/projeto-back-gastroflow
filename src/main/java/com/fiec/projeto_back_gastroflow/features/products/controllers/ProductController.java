package com.fiec.projeto_back_gastroflow.features.products.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/products")
public class ProductController {

    @GetMapping
    public void Getproduct(){
        System.out.println("hello world");
    }
}
