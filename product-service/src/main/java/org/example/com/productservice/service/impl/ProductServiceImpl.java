package org.example.com.productservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.com.productservice.entity.Product;
import org.example.com.productservice.mapper.ProductMapper;
import org.example.com.productservice.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl
        extends ServiceImpl<ProductMapper, Product>
        implements ProductService {
}