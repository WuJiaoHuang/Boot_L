package org.example.com.productservice.controller;

import org.example.com.productservice.common.Result;
import org.example.com.productservice.entity.Product;
import org.example.com.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    @GetMapping
    public Result<List<Product>> list() {
        return Result.success(productService.list());
    }

    @PostMapping
    public Result<Void> add(@RequestBody Product product) {
        productService.save(product);
        return Result.success();
    }
}