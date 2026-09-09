package org.example.com.productservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.com.productservice.entity.Product;
import org.example.com.productservice.mapper.ProductMapper;
import org.example.com.productservice.service.ProductService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl
        extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    private final StringRedisTemplate stringRedisTemplate;

    public ProductServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        String key = "product:"+id;
        String json = stringRedisTemplate.opsForValue().get(key);
        return null;
    }
}