package com.kaleem.springsecurity.controller;

import com.kaleem.springsecurity.model.Product;
import com.kaleem.springsecurity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findAll()
    {
        return productService.findAll();
    }

    @PostMapping
    public Product save(@RequestBody Product product)
    {
        return productService.save(product);
    }

}
